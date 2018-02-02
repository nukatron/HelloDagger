package com.nutron.hellodagger.presentations.empty

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nutron.hellodagger.R
import com.nutron.hellodagger.domain.GetNumberInteractor
import com.nutron.hellodagger.extensions.logd
import com.nutron.hellodagger.extensions.viewComponent
import javax.inject.Inject

/**
 * Created by nutron on 10/16/17.
 */

class EmptyFragment : Fragment() {

    /**
     * Inject GetNumberInteractor to this fragment
     */
    @Inject lateinit var interactor: GetNumberInteractor

    companion object {
        fun getInstance() = EmptyFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logd("RandomFragment # viewComponent $viewComponent")
        viewComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // verify the GetNumberInteractor memory object ID
        logd("SecondFragment # Interactor $interactor")
        return inflater.inflate(R.layout.fragment_empty, container, false)
    }
}