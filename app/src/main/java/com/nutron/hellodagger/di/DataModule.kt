package com.nutron.hellodagger.di

import com.nutron.hellodagger.common.DI_NAME_VALUE_LOCAL_DATA_SOURCE
import com.nutron.hellodagger.common.DI_NAME_VALUE_REMOTE_DATA_SOURCE
import com.nutron.hellodagger.data.ValueDataSource
import com.nutron.hellodagger.data.ValueLocalDataSource
import com.nutron.hellodagger.data.ValueRemoteDataSource
import com.nutron.hellodagger.data.preference.ValuePreference
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

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