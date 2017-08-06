package com.nutron.hellodagger.domain

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


interface SchedulerProvider {
    fun <T> applyDefaultSchedulers(): ObservableTransformer<T, T>
}

class SchedulerProviderImpl : SchedulerProvider {
    override fun <T> applyDefaultSchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

}