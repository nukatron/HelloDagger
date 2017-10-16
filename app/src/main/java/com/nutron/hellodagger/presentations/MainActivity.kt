package com.nutron.hellodagger.presentations

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nutron.hellodagger.R
import com.nutron.hellodagger.presentations.empty.EmptyFragment
import com.nutron.hellodagger.presentations.landing.LandingFragment
import com.nutron.hellodagger.presentations.random.RandomFragment

class MainActivity : AppCompatActivity(), LandingFragment.Callback {

    companion object {
        fun getStartIntent(context: Context) = Intent(context, MainActivity::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.root, LandingFragment.getInstance())
                .commit()
    }

    override fun gotoPageOne() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.root, RandomFragment.getInstance(), "RandomFragment")
                .addToBackStack(null)
                .commit()
    }

    override fun gotoPageTwo() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.root, EmptyFragment.getInstance(), "EmptyFragment")
                .addToBackStack(null)
                .commit()
    }

}
