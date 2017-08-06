package com.nutron.hellodagger.presentations.random

import com.jakewharton.rxrelay2.PublishRelay
import com.nutron.hellodagger.common.ParentRefDelegate
import com.nutron.hellodagger.data.entity.ValueEntity
import com.nutron.hellodagger.domain.GetNumberInteractor
import com.nutron.hellodagger.extensions.elements
import com.nutron.hellodagger.extensions.error
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


interface RandomViewModel {

    val input: Input
    val output: Output

    interface Input {
        val active: PublishRelay<Unit>
        val refresh: Consumer<Unit>
        val clearMemory: Consumer<Unit>
        val ClearMemoryAndDisk: Consumer<Unit>
    }

    interface Output {
        val showProgress: Observable<Boolean>
        val number: Observable<ValueEntity>
        val error: Observable<Throwable>
        val showToast: Observable<String>
        val enableButton: Observable<Boolean>
    }

}

class RandomViewModelImpl(val numberInteractor: GetNumberInteractor) :
        RandomViewModel, RandomViewModel.Input, RandomViewModel.Output {


    override val input: RandomViewModel.Input by ParentRefDelegate()
    override val output: RandomViewModel.Output by ParentRefDelegate()

    // input
    override val active: PublishRelay<Unit> = PublishRelay.create()
    override val refresh: PublishRelay<Unit> = PublishRelay.create()
    override val clearMemory: PublishRelay<Unit> = PublishRelay.create()
    override val ClearMemoryAndDisk: PublishRelay<Unit> = PublishRelay.create()

    // output
    override val showProgress: Observable<Boolean>
    override val number: Observable<ValueEntity>
    override val error: Observable<Throwable>
    override val showToast: Observable<String>
    override val enableButton: Observable<Boolean>

    init {

        val inputTrigger = Observable.merge(active, refresh)
                .debounce(300, TimeUnit.MICROSECONDS)
                .share()
        val shareObservable = inputTrigger.observeOn(Schedulers.io())
                .flatMap { numberInteractor.buildObservable().materialize()}
                .share()

        val startActiveProgress = inputTrigger.map { true }
        val stopActiveProgress = shareObservable.map { false }
        val processingObservable = Observable.merge(startActiveProgress, stopActiveProgress).share()
        showProgress = processingObservable
        enableButton = processingObservable.map { !it }

        error = shareObservable.error()
        number = shareObservable.elements()

        val clearMemObservable = clearMemory.observeOn(Schedulers.computation())
                .flatMap {
                    /** we need to do 'materialize' because 'clearMemoryCache' return 'Completable',
                     * so it will never trigger ourout put because
                     * our output always need 'element' (onNext), NOT 'onCompleted'*/
                    numberInteractor.clearMemoryCache()
                            .toObservable<String>()
                            .materialize()
                }.map { "clear memory success" }

        val clearMemDiskObservable = ClearMemoryAndDisk.observeOn(Schedulers.computation())
                .flatMap {
                    /** we need to do 'materialize' because 'clearMemoryAndDiskCache'
                     * return 'Completable', so it will never trigger our output
                     * because our output always need 'element' (onNext), NOT 'onCompleted'*/
                    numberInteractor.clearMemoryAndDiskCache()
                            .toObservable<String>()
                            .materialize()
                }.map {  "clear Memory and Disk" }

        showToast = Observable.merge(clearMemObservable, clearMemDiskObservable)
    }
}