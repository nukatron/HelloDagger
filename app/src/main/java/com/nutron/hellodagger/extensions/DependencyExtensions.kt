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
val Context.domainComponent: DomainSubComponent
    get() = createDomainComponent()

val Fragment.domainComponent: DomainSubComponent
    get() = context.createDomainComponent()

fun Context.createDomainComponent(): DomainSubComponent {
    MainApplication._domainComponent =  MainApplication._domainComponent ?:
            MainApplication._appComponent.plusDomainSubComponent(DomainModule())
    return MainApplication._domainComponent!!
}

fun Context.clearDomainComponent() {
    MainApplication._viewComponent = null
}

/**
 * extension function for 'ViewComponent'
 */
val Context.viewComponent: ViewSubComponent
    get() = createViewComponent()

val Fragment.viewComponent: ViewSubComponent
    get() = context.createViewComponent()

fun Context.createViewComponent(): ViewSubComponent {
    MainApplication._viewComponent =  MainApplication._viewComponent ?:
            MainApplication._appComponent
                    .plusDomainSubComponent(DomainModule())
                    .plusViewSubComponent(ViewModelModule())
    return MainApplication._viewComponent!!
}

fun Context.clearViewComponent() {
    MainApplication._viewComponent = null
}