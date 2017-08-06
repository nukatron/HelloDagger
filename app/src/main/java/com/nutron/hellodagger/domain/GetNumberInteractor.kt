package com.nutron.hellodagger.domain

import com.nutron.hellodagger.common.DI_NAME_VALUE_LOCAL_DATA_SOURCE
import com.nutron.hellodagger.common.DI_NAME_VALUE_REMOTE_DATA_SOURCE
import com.nutron.hellodagger.data.ValueDataSource
import com.nutron.hellodagger.data.entity.ValueEntity
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named


class GetNumberInteractor @Inject constructor(
        private val schedulerProvider: SchedulerProvider,
        private @Named(DI_NAME_VALUE_LOCAL_DATA_SOURCE) val localDataSource: ValueDataSource,
        private @Named(DI_NAME_VALUE_REMOTE_DATA_SOURCE) val remoteDataSource: ValueDataSource,
        private val clockManager: ClockManager) : ObservableUseCase<ValueEntity> {

    private val STALE_MS = (5 * 1000).toLong()

    private var memoryCache: ValueEntity? = null

    override fun buildObservable(): Observable<ValueEntity> {
        return Observable.concat(listOf(fetchFromMemory(), fetchFromDisk(), fetchFromRemote()))
                .filter { isUpToDate(it) }
                .firstElement()
                .toObservable()
    }

    fun clearMemoryCache(): Completable = Completable.fromAction { memoryCache = null }

    fun clearMemoryAndDiskCache(): Completable = clearMemoryCache().andThen(localDataSource.clearValue())

    private fun fetchFromRemote(): Observable<ValueEntity> {
        return remoteDataSource.getValue()
                .doOnNext { memoryCache = it }
                .flatMap { entity ->
                    localDataSource.saveValue(entity)
                            // convert completable back to Observable in order to use 'materialize'
                            .toObservable<ValueEntity>()
                            // in order to trigger 'map'
                            .materialize()
                            // in order to return 'element' to main steam, not 'completed'
                            .map { entity }
                }
                .compose(schedulerProvider.applyDefaultSchedulers())
    }

    private fun fetchFromDisk(): Observable<ValueEntity> {
        return localDataSource.getValue()
                .doOnNext { memoryCache = it }
                .compose(schedulerProvider.applyDefaultSchedulers())
    }

    private fun fetchFromMemory(): Observable<ValueEntity> {
        memoryCache?.source = "Memory"
        return memoryCache?.let { Observable.just(it) } ?: Observable.empty()
    }

    private fun isUpToDate(valueEntity: ValueEntity): Boolean {
        val diff = clockManager.millis() - valueEntity.timeStamp
        return diff < STALE_MS
    }
}