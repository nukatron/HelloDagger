package com.nutron.hellodagger.presentations.landing

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.nutron.hellodagger.R
import kotlinx.android.synthetic.main.fragment_landing.*


class LandingFragment : Fragment() {

    interface Callback {
        fun gotoPageOne()
        fun gotoPageTwo()
    }

    private var listtener: Callback? = null

    companion object {
        fun getInstance(): LandingFragment {
            return LandingFragment()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listtener = context as? Callback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_landing, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RxView.clicks(pageOneBtn).subscribe {
            listtener?.gotoPageOne()
        }

        RxView.clicks(pageTwoBtn).subscribe {
            listtener?.gotoPageTwo()
        }
    }
}