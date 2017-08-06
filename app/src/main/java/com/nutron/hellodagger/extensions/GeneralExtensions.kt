package com.nutron.hellodagger.extensions

import android.util.Log


inline fun <reified T> T.logv(message: String?, TAG: String = T::class.java.simpleName) {
    Log.v(TAG, message)
}

inline fun <reified T> T.logd(message: String?, TAG: String = T::class.java.simpleName) {
    Log.d(TAG, message)
}

inline fun <reified T> T.logi(message: String?, TAG: String = T::class.java.simpleName) {
    Log.i(TAG, message)
}

inline fun <reified T> T.loge(message: String?, TAG: String = T::class.java.simpleName, throwable: Throwable? = null) {
    throwable?.let {  Log.e(TAG, message, it) } ?: Log.e(TAG, message)
}