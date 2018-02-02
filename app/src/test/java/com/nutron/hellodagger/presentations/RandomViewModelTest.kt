package com.nutron.hellodagger.presentations

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.nutron.hellodagger.data.entity.ValueEntity
import com.nutron.hellodagger.domain.GetNumberInteractor
import com.nutron.hellodagger.presentations.random.RandomViewModel
import com.nutron.hellodagger.presentations.random.RandomViewModelImpl
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit


class RandomViewModelTest {

    private lateinit var viewModel : RandomViewModel
    private val testScheduler = TestScheduler()
    private val getNumberInteractor: GetNumberInteractor = mock()
    private val mockValue = ValueEntity(10, System.currentTimeMillis(), "Disk")


    @Before
    fun setup() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler{ Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        // for debounce that use Computation by default
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }

        viewModel = RandomViewModelImpl(getNumberInteractor)
    }

    @After
    fun tearDown() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
    }

    @Test
    fun testShowProgress_showAndHide() {
        doReturn(Observable.just(mockValue)).whenever(getNumberInteractor).buildObservable()
        val progressObserver = viewModel.output.showProgress.test()

        viewModel.input.active.accept(Unit)
        //advance time for debounce
        testScheduler.advanceTimeBy(400, TimeUnit.MILLISECONDS)
        testScheduler.triggerActions()
        // showProgress observable should emit true and false
        progressObserver.assertValues(true, false)
    }

    @Test
    fun testEnableButton_showAndHide() {
        doReturn(Observable.just(mockValue)).whenever(getNumberInteractor).buildObservable()
        val enableButtonObservable = viewModel.output.enableButton.test()
        viewModel.input.active.accept(Unit)
        //advance time for debounce
        testScheduler.advanceTimeBy(400, TimeUnit.MILLISECONDS)
        testScheduler.triggerActions()
        // showNumber observable should emit true and false
        enableButtonObservable.assertValues(false, true)
    }

    @Test
    fun testClearMemory_showToast_success () {
        doReturn(Completable.complete()).whenever(getNumberInteractor).clearMemoryCache()

        val showToastTestObserver = viewModel.output.showToast.test()
        viewModel.input.clearMemory.accept(Unit)
        testScheduler.triggerActions()

        showToastTestObserver.assertValue("clear memory success")

    }

    @Test
    fun testClearMemory_showToast_error () {
        val exception = Exception("clear memory failed")
        doReturn(Completable.error(exception)).whenever(getNumberInteractor).clearMemoryCache()
        val errorTestObserver = viewModel.output.error.test()

        viewModel.input.clearMemory.accept(Unit)
        testScheduler.triggerActions()

        errorTestObserver.assertValue(exception)
    }

    @Test
    fun testClearMemoryAndDisk_showToast_success () {
        doReturn(Completable.complete()).whenever(getNumberInteractor).clearMemoryAndDiskCache()

        val showToastTestObserver = viewModel.output.showToast.test()
        viewModel.input.clearMemoryAndDisk.accept(Unit)
        testScheduler.triggerActions()

        showToastTestObserver.assertValue("clear Memory and Disk")

    }

    @Test
    fun testClearMemoryAndDisk_showToast_error () {
        val exception = Exception("clear memory failed")
        doReturn(Completable.error(exception)).whenever(getNumberInteractor).clearMemoryAndDiskCache()
        val errorTestObserver = viewModel.output.error.test()

        viewModel.input.clearMemoryAndDisk.accept(Unit)
        testScheduler.triggerActions()

        errorTestObserver.assertValue(exception)
    }

    @Test
    fun testShowNumber_fromMemory() {

        val numberTestScheduler = viewModel.output.number.test()
        val time = System.currentTimeMillis()
        val mockValue = ValueEntity(10, time, "Remote")
        doReturn(Observable.just(mockValue)).whenever(getNumberInteractor).buildObservable()

        viewModel.input.active.accept(Unit)
        testScheduler.advanceTimeBy(400, TimeUnit.MILLISECONDS)
        testScheduler.triggerActions()
        numberTestScheduler.assertValue(mockValue)

    }

}