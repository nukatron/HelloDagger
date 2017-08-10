package com.nutron.hellodagger.di

import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass


// Needed only to to create the above mapping
@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SubComponentKey(val value: KClass<*>)

@Module(subcomponents = arrayOf(DomainSubComponent::class, ViewSubComponent::class))
abstract class ApplicationBinderModule {
    // Provide the builder to be included in a mapping used for creating the builders.
    @Binds
    @IntoMap
    @SubComponentKey(DomainSubComponent.Builder::class)
    abstract fun domain(impl: DomainSubComponent.Builder): SubComponentBuilder<*>

    @Binds
    @IntoMap
    @SubComponentKey(ViewSubComponent.Builder::class)
    abstract fun view(impl: ViewSubComponent.Builder): SubComponentBuilder<*>
}

