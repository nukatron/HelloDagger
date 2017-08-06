package com.nutron.hellodagger.di

import com.nutron.hellodagger.presentations.random.RandomFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class, ViewModelModule::class, DataModule::class, DomainModule::class))
interface AppComponent {
    fun inject(fragment: RandomFragment)
}