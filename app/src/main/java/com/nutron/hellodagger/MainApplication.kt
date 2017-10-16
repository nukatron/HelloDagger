package com.nutron.hellodagger

import android.app.Application
import com.nutron.hellodagger.data.preference.Preferences
import com.nutron.hellodagger.di.*
import com.nutron.hellodagger.extensions.logd


class MainApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    var viewComponent: ViewSubComponent? = null
        set(value) = if (value == null) clearViewComponent() else field = value
        get() {
            if(field == null) field = initViewComponent()
            return field
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

    private fun initViewComponent(): ViewSubComponent {
        val builder = appComponent.subComponentBuilders()[ViewSubComponent.Builder::class.java]?.get()
                as? ViewSubComponent.Builder
        // it should be crash if the object doesn't has
        return builder?.domainModule(DomainModule()) // you can ignore this line because the module doesn't take param
                ?.viewModule(ViewModelModule()) // you can ignore this line because the module doesn't take param
                ?.build()!!
    }

    public fun clearViewComponent() {
        viewComponent = null
    }
}
