package com.nutron.hellodagger.di

import com.nutron.hellodagger.domain.*
import dagger.Module
import dagger.Provides


@Module
class DomainModule {

    @Provides
    fun provideClockManager(): ClockManager = ClockManagerImpl()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = SchedulerProviderImpl()

}