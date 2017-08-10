package com.nutron.hellodagger.di

import com.nutron.hellodagger.presentations.random.RandomFragment
import dagger.Component
import dagger.Subcomponent
import javax.inject.Provider
import javax.inject.Singleton


interface SubComponentBuilder<out V> {
    fun build(): V
}

@Singleton
@Component(modules = arrayOf(AppModule::class, DataModule::class, ApplicationBinderModule::class))
interface AppComponent {
    // Returns a map with all the builders mapped by their class.
    fun subComponentBuilders(): Map<Class<*>, Provider<SubComponentBuilder<*>>>
}


@DomainScope
@Subcomponent(modules = arrayOf(DomainModule::class))
interface DomainSubComponent {
    // injection targets here

    @Subcomponent.Builder
    interface Builder : SubComponentBuilder<DomainSubComponent> {
        fun domainModule(module: DomainModule): Builder
    }
}

@ViewScope
@Subcomponent(modules = arrayOf(ViewModelModule::class, DomainModule::class))
interface ViewSubComponent {
    // injection targets here
    fun inject(fragment: RandomFragment)

    @Subcomponent.Builder
    interface Builder : SubComponentBuilder<ViewSubComponent> {
        fun viewModule(module: ViewModelModule): Builder
        fun domainModule(module: DomainModule): Builder
    }
}