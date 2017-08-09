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
    fun plusDomainSubComponent(domainModule: DomainModule): DomainSubComponent
}


@DomainScope
@Subcomponent(modules = arrayOf(DomainModule::class))
interface DomainSubComponent {
    // injection targets here

    // factory method to instantiate the subcomponent defined here (passing in the module instance)
    fun plusViewSubComponent(viewModelModule: ViewModelModule): ViewSubComponent

}

@ViewScope
@Subcomponent(modules = arrayOf(ViewModelModule::class))
interface ViewSubComponent {
    // injection targets here
    fun inject(fragment: RandomFragment)

    // factory method to instantiate the subcomponent defined here (passing in the module instance)
}