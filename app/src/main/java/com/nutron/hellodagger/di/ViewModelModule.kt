package com.nutron.hellodagger.di

import com.nutron.hellodagger.domain.GetNumberInteractor
import com.nutron.hellodagger.presentations.random.RandomViewModel
import com.nutron.hellodagger.presentations.random.RandomViewModelImpl
import dagger.Module
import dagger.Provides


@Module
class ViewModelModule {

    @Provides
    fun provideRandomViewModel(numberInteractor: GetNumberInteractor): RandomViewModel = RandomViewModelImpl(numberInteractor)
}