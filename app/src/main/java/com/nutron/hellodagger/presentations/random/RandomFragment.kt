package com.nutron.hellodagger.presentations.random

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import com.nutron.hellodagger.R
import com.nutron.hellodagger.domain.GetNumberInteractor
import com.nutron.hellodagger.extensions.addTo
import com.nutron.hellodagger.extensions.logd
import com.nutron.hellodagger.extensions.loge
import com.nutron.hellodagger.extensions.viewComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_random.*
import javax.inject.Inject


class RandomFragment: Fragment() {

    @Inject lateinit var viewModel: RandomViewModel
    @Inject lateinit var numberInteractor: GetNumberInteractor

    private val disposeBag = CompositeDisposable()

    companion object {
        val TAG = RandomFragment::class.java.canonicalName
        fun getInstance(): RandomFragment {
            return RandomFragment()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inject Dagger
        logd("RandomFragment # viewComponent $viewComponent")
        viewComponent.inject(this)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        logd("RandomFragment # Interactor $numberInteractor")
        return inflater.inflate(R.layout.fragment_random, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOutput()
        initInput()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.clear()
    }

    private fun initInput() {
        RxView.clicks(randomRefreshBtn)
                .map { Unit }
                .subscribe(viewModel.input.refresh)
                .addTo(disposeBag)

        RxView.clicks(randomClearMemoryCacheBtn)
                .map { Unit }
                .subscribe(viewModel.input.clearMemory)
                .addTo(disposeBag)

        RxView.clicks(randomClearMemoryAndDiskCacheBtn)
                .map { Unit }
                .subscribe(viewModel.input.clearMemoryAndDisk)
                .addTo(disposeBag)

        viewModel.input.active.accept(Unit)
    }

    private fun initOutput() {
        viewModel.output.number.observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    randomNumberTextView.text = it.value.toString()
                    randomSourceTextView.text = "Source: ${it.source}"
                }
                .addTo(disposeBag)

        viewModel.output.showProgress.observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    randomLoadingProgressBar.visibility = if(it) View.VISIBLE else View.GONE
                    randomNumberTextView.visibility = if(it)  View.GONE else View.VISIBLE
                }
                .addTo(disposeBag)

        viewModel.output.error.observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleError)
                .addTo(disposeBag)

        viewModel.output.enableButton.observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    randomRefreshBtn.isEnabled = it
                    randomClearMemoryCacheBtn.isEnabled = it
                    randomClearMemoryAndDiskCacheBtn.isEnabled = it
                }.addTo(disposeBag)

        viewModel.output.showToast.observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showToast)
                .addTo(disposeBag)
    }

    private fun showToast(txt: String) {
        Toast.makeText(activity, txt, Toast.LENGTH_LONG).show()
    }

    private fun handleError(t: Throwable) {
        Toast.makeText(activity, t.message, Toast.LENGTH_LONG).show()
        loge(message = t.message, throwable = t)
    }
}