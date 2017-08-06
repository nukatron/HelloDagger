package com.nutron.hellodagger.data.preference


class ValuePreference: Preferences() {
    var value by intPref(defaultValue = -1)
    var timeStamp by longPref()

    override fun remove(vararg kefPrefs: String) {
        super.remove("value", "timeStamp")
    }
}

