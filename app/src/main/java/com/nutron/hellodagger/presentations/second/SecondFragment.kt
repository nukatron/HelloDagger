package com.nutron.hellodagger.presentations.second

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nutron.hellodagger.MainApplication
import com.nutron.hellodagger.R
import com.nutron.hellodagger.domain.GetNumberInteractor
import com.nutron.hellodagger.extensions.logd
import javax.inject.Inject


class SecondFragment : Fragment() {

    @Inject lateinit var interactor: GetNumberInteractor

    companion object {
        fun getInstance(): SecondFragment {
            return SecondFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApplication.viewComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        logd("SecondFragment # Interactor $interactor")
        return inflater?.inflate(R.layout.fragment_random, container, false)
    }


}