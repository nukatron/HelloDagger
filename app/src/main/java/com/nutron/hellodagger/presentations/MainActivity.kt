package com.nutron.hellodagger.presentations

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.nutron.hellodagger.R
import com.nutron.hellodagger.presentations.random.RandomFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.root, RandomFragment.getInstance())
                .commit()
    }


}
