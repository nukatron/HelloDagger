package com.nutron.hellodagger.di

import com.nutron.hellodagger.presentations.random.RandomFragment
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class, DataModule::class))
interface AppComponent {
    // injection targets here

    // factory method to instantiate the subcomponent defined here (passing in the module instance)
    fun plusDomainComponent(domainModule: DomainModule): DomainComponent
}


@DomainScope
@Subcomponent(modules = arrayOf(DomainModule::class))
interface DomainComponent {
    // injection targets here

    // factory method to instantiate the subcomponent defined here (passing in the module instance)
    fun plusViewComponent(viewModelModule: ViewModelModule): ViewComponent

}

@ViewScope
@Subcomponent(modules = arrayOf(ViewModelModule::class))
interface ViewComponent {
    // injection targets here
    fun inject(fragment: RandomFragment)

    // factory method to instantiate the subcomponent defined here (passing in the module instance)
}