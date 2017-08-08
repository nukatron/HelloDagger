package com.nutron.hellodagger.extensions

import android.content.Context
import android.support.v4.app.Fragment
import com.nutron.hellodagger.MainApplication
import com.nutron.hellodagger.di.*

/**
 * extension function for 'AppComponent'
 */
val Context.component: AppComponent
    get() = MainApplication._appComponent

val Fragment.component: AppComponent
    get() = MainApplication._appComponent


/**
 * extension function for 'DomainComponent'
 */
val Context.domainComponent: DomainComponent
    get() = createDomainComponent()

val Fragment.domainComponent: DomainComponent
    get() = context.createDomainComponent()

fun Context.createDomainComponent(): DomainComponent {
    MainApplication._domainComponent =  MainApplication._domainComponent ?:
            MainApplication._appComponent.plusDomainComponent(DomainModule())
    return MainApplication._domainComponent!!
}

fun Context.clearDomainComponent() {
    MainApplication._viewComponent = null
}

/**
 * extension function for 'ViewComponent'
 */
val Context.viewComponent: ViewComponent
    get() = createViewComponent()

val Fragment.viewComponent: ViewComponent
    get() = context.createViewComponent()

fun Context.createViewComponent(): ViewComponent {
    MainApplication._viewComponent =  MainApplication._viewComponent ?:
            MainApplication._appComponent
                    .plusDomainComponent(DomainModule())
                    .plusViewComponent(ViewModelModule())
    return MainApplication._viewComponent!!
}

fun Context.clearViewComponent() {
    MainApplication._viewComponent = null
}