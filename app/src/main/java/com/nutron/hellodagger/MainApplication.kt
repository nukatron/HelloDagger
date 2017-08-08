package com.nutron.hellodagger

import android.app.Application
import com.nutron.hellodagger.data.preference.Preferences
import com.nutron.hellodagger.di.*


class MainApplication : Application() {

    companion object {
        lateinit var _appComponent: AppComponent
        var _viewComponent: ViewComponent? = null
        var _domainComponent: DomainComponent? = null
    }

    override fun onCreate() {
        super.onCreate()
        Preferences.init(this)
        _appComponent = initDagger(this)
    }

    @Suppress("DEPRECATION")
    private fun initDagger(application: MainApplication): AppComponent {
        return DaggerAppComponent.builder().appModule(AppModule(application)).build()
    }
}
