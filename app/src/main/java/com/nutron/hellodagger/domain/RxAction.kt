package com.nutron.hellodagger.domain

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


@Suppress("UNCHECKED_CAST")
class Action<I, O>(val work: (I) -> Observable<O>) {

    val inputs: PublishSubject<I>
    val elements: Observable<O>
    val errors: Observable<Throwable>
    val isExecuting: Observable<Boolean>

    init {

        val activityIndicator = ActivityIndicator()
        isExecuting = activityIndicator.asObservable()

        inputs = PublishSubject.create()

        val result = inputs.flatMap {
            work(it).trackActivity(activityIndicator).materialize()
        }.share()

        elements = result
                .filter { it.value != null }
                .map { it.value!! }

        errors = result
                .filter { it.error != null }
                .map { it.error!! }

    }
}

//Example
fun example() {

    val somethingAction = Action<Unit, String> {
        return@Action Observable.just("call api")
    }

    somethingAction.inputs
    somethingAction.isExecuting
    somethingAction.elements
    somethingAction.errors
}