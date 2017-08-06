package com.nutron.hellodagger.common

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


@Suppress("UNCHECKED_CAST")
class ParentRefDelegate<in R, out T> : ReadOnlyProperty<R, T> {
    override fun getValue(thisRef: R, property: KProperty<*>): T {
        return thisRef as T
    }

}