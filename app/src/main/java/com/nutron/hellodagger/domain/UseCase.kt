package com.nutron.hellodagger.domain

import io.reactivex.Completable
import io.reactivex.Observable


interface UseCase

interface SimpleUseCase<out T> : UseCase {
    fun execute(): T
}

interface ObservableUseCase<T> : UseCase {
    fun buildObservable(): Observable<T>
}

interface CompletableUseCase : UseCase {
    fun buildCompletable(): Completable
}