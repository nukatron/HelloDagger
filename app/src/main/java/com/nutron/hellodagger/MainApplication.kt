package com.nutron.hellodagger

import android.app.Application
import com.nutron.hellodagger.data.preference.Preferences
import com.nutron.hellodagger.di.*


class MainApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var viewComponent: ViewSubComponent
    }

    override fun onCreate() {
        super.onCreate()
        Preferences.init(this)
        appComponent = initDagger(this)
        viewComponent = initViewComponent()
    }

    @Suppress("DEPRECATION")
    private fun initDagger(application: MainApplication): AppComponent {
        return DaggerAppComponent.builder().appModule(AppModule(application)).build()
    }

    private fun initViewComponent(): ViewSubComponent {
        val builder = appComponent
                .subComponentBuilders()[ViewSubComponent.Builder::class.java]?.get()
                as? ViewSubComponent.Builder
        // it should be crash if the object doesn't has
        return builder
                ?.domainModule(DomainModule()) // you can ignore this line because the module doesn't take param
                ?.viewModule(ViewModelModule()) // you can ignore this line because the module doesn't take param
                ?.build()!!


    }
}
