package com.nutron.hellodagger.di

import android.app.Application
import android.content.Context
import com.nutron.hellodagger.common.DI_NAME_VALUE_LOCAL_DATA_SOURCE
import com.nutron.hellodagger.common.DI_NAME_VALUE_REMOTE_DATA_SOURCE
import com.nutron.hellodagger.data.ValueDataSource
import com.nutron.hellodagger.data.ValueLocalDataSource
import com.nutron.hellodagger.data.ValueRemoteDataSource
import com.nutron.hellodagger.data.preference.ValuePreference
import com.nutron.hellodagger.domain.*
import com.nutron.hellodagger.presentations.random.RandomViewModel
import com.nutron.hellodagger.presentations.random.RandomViewModelImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Define AppModule
 */
@Module
class AppModule(val context: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = context
}

/**
 * Define DataModule
 */
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideValuePreferences(): ValuePreference = ValuePreference()

    @Provides
    @Singleton
    @Named(DI_NAME_VALUE_LOCAL_DATA_SOURCE)
    fun provideValueLocalDataSource(pref: ValuePreference): ValueDataSource = ValueLocalDataSource(pref)

    @Provides
    @Singleton
    @Named(DI_NAME_VALUE_REMOTE_DATA_SOURCE)
    fun provideValueRemoteDataSource(): ValueDataSource = ValueRemoteDataSource()
}


/**
 * Define DomainModule
 */
@Module
class DomainModule {

    @Provides
    fun provideClockManager(): ClockManager = ClockManagerImpl()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = SchedulerProviderImpl()

}

/**
 * Define ViewModelModule
 */
@Module
class ViewModelModule {

    @Provides
    fun provideRandomViewModel(numberInteractor: GetNumberInteractor): RandomViewModel = RandomViewModelImpl(numberInteractor)
}