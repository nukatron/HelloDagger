package com.nutron.hellodagger

import android.app.Application
import android.content.Context
import android.support.v4.app.Fragment
import com.nutron.hellodagger.data.preference.Preferences
import com.nutron.hellodagger.di.AppComponent
import com.nutron.hellodagger.di.AppModule
import com.nutron.hellodagger.di.DaggerAppComponent


class MainApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        Preferences.init(this)
        appComponent = initDagger(this)
    }

    @Suppress("DEPRECATION")
    private fun initDagger(application: MainApplication): AppComponent {
        return DaggerAppComponent.builder().appModule(AppModule(application)).build()
    }
}

val Context.component: AppComponent
    get() = MainApplication.appComponent

val Fragment.component: AppComponent
    get() = MainApplication.appComponent