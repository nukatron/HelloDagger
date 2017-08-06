package com.nutron.hellodagger.extensions

import io.reactivex.Notification
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers


@Suppress("NOTHING_TO_INLINE")
inline fun <T> Observable<T>.doInIo(): Observable<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread(), true)

@Suppress("NOTHING_TO_INLINE")
inline fun Disposable.addTo(compositeDisposable: CompositeDisposable): Boolean = compositeDisposable.add(this)

@Suppress("NOTHING_TO_INLINE")
operator inline fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}

@Suppress("NOTHING_TO_INLINE")
inline fun <T> Observable<Notification<T>>.elements() = this.filter { it.value != null }.map { it.value!! }

@Suppress("NOTHING_TO_INLINE")
inline fun <T> Observable<Notification<T>>.completed() = this.filter { it.isOnComplete }.map { true }

@Suppress("NOTHING_TO_INLINE")
inline fun <T> Observable<Notification<T>>.error() = this.filter { it.error != null }.map { it.error!! }

inline fun <T, U, R> Observable<T>.withLatestFromSimple(other: ObservableSource<out U>, crossinline combiner: (T, U) -> R): Observable<R> {
    return this.withLatestFrom(other, BiFunction<T, U, R> { t1, t2 ->
        return@BiFunction combiner(t1, t2)
    })
}