package com.nutron.hellodagger.presentations

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nutron.hellodagger.R
import com.nutron.hellodagger.presentations.landing.LandingFragment
import com.nutron.hellodagger.presentations.random.RandomFragment
import com.nutron.hellodagger.presentations.second.SecondFragment

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
                .addToBackStack(null)
                .commit()
    }

    override fun gotoPageOne() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.root, RandomFragment.getInstance())
                .addToBackStack(null)
                .commit()
    }

    override fun gotoPageTwo() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.root, SecondFragment.getInstance(), "SecondFragment")
                .addToBackStack(null)
                .commit()
    }

}
