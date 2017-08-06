package com.nutron.hellodagger.domain


interface ClockManager {
    fun millis(): Long
}

class ClockManagerImpl : ClockManager {
    override fun millis() = System.currentTimeMillis()
}