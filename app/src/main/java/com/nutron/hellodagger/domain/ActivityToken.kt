package com.nutron.hellodagger.domain

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.subjects.BehaviorSubject


class ActivityToken<E>(private var _source: Observable<E>, run: (() -> Unit)): Disposable, ObservableSource<E> {

    private val _dispose: Disposable = Disposables.fromAction(run)

    override fun dispose() {
        return _dispose.dispose()
    }

    override fun isDisposed(): Boolean {
        return _dispose.isDisposed
    }

    override fun subscribe(observer: Observer<in E>) = _source.subscribe(observer)

    fun asObservable(): Observable<E> {
        return _source
    }
}
class ActivityIndicator: ObservableSource<Boolean> {

    private var _variable: BehaviorSubject<Int> = BehaviorSubject.create<Int>()
    private var _loading: Observable<Boolean>

    init {
        _variable.onNext(0)
        _loading = _variable.map {
            it > 0
        }.distinctUntilChanged()
    }

    fun <E>trackActivityOfObservable(source: Observable<E>): Observable<E> {

        return Observable.using({
            this.increment()
            ActivityToken<E>(source, this::decrement)
        }, { t: ActivityToken<E> ->
            t.asObservable()
        }) { t: ActivityToken<E> ->
            t.dispose()
        }
    }

    private fun increment() {
        _variable.onNext(_variable.value + 1)
    }

    private fun decrement() {
        _variable.onNext(_variable.value - 1)
    }

    override fun subscribe(observer: Observer<in Boolean>) = _loading.subscribe(observer)

    fun asObservable(): Observable<Boolean> {
        return _loading
    }
}

fun <T>io.reactivex.Observable<T>.trackActivity(indicator: ActivityIndicator): Observable<T> {
    return indicator.trackActivityOfObservable(this)
}