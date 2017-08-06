package com.nutron.hellodagger.data

import com.nutron.hellodagger.data.entity.ValueEntity
import com.nutron.hellodagger.data.preference.ValuePreference
import com.nutron.hellodagger.extensions.loge
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


interface ValueDataSource {

    fun getValue(): Observable<ValueEntity>
    /** try to avoid using Completable because a subscriber will never get `onNext`
     * that make our output never call `onNext`*/
    fun saveValue(entity: ValueEntity): Completable
    fun clearValue(): Completable
}


class ValueLocalDataSource @Inject constructor(val prefs: ValuePreference) : ValueDataSource {

    private val NO_VALUE = -1

    override fun getValue(): Observable<ValueEntity> = Observable.defer {
        if (prefs.value != NO_VALUE) {
            return@defer Observable.just(ValueEntity(prefs.value, prefs.timeStamp, "Disk"))
        }
        return@defer Observable.empty<ValueEntity>()
    }

    override fun saveValue(entity: ValueEntity): Completable = Completable.fromAction {
        prefs.value = entity.value
        prefs.timeStamp = entity.timeStamp
    }.doOnError { loge(it.message) }.onErrorComplete()

    override fun clearValue(): Completable = Completable.fromAction { prefs.remove() }
            .doOnError { loge(it.message) }.onErrorComplete()
}


/**
 * This is faking a REST service returning an observable
 */
class ValueRemoteDataSource : ValueDataSource {

    override fun getValue(): Observable<ValueEntity> = Observable.defer {
        val random = Random()
        val value = ValueEntity(random.nextInt(50), System.currentTimeMillis(), "Remote")
        return@defer Observable.just(value)
    }.delay(3, TimeUnit.SECONDS)

    override fun saveValue(entity: ValueEntity): Completable = Completable.complete()

    override fun clearValue(): Completable = Completable.complete()

}