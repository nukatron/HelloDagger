package com.nutron.hellodagger.extensions

import android.content.SharedPreferences


inline fun SharedPreferences.edit(func: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.func()
    editor.apply()
}