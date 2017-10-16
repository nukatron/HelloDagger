package com.nutron.hellodagger.extensions

import android.app.Activity
import android.content.Context
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import com.nutron.hellodagger.MainApplication
import com.nutron.hellodagger.di.AppComponent
import com.nutron.hellodagger.di.ViewSubComponent

/**
 * Created by nutron on 10/16/17.
 */

/**
 * extension function for 'AppComponent'
 */
val Context.component: AppComponent
    get() = MainApplication.appComponent

val Fragment.component: AppComponent
    get() = MainApplication.appComponent

val Activity.component: AppComponent
    get() = MainApplication.appComponent

val Fragment.viewComponent: ViewSubComponent
    get() = (activity.application as MainApplication).viewComponent!!

val Activity.viewComponent: ViewSubComponent
    get() = (application as MainApplication).viewComponent!!

inline fun <reified T: Activity> T.clearViewComponent() {
    (application as MainApplication).clearViewComponent()
}

inline fun <reified T: Fragment> T.clearViewComponent() {
    (activity.application as MainApplication).clearViewComponent()
}
