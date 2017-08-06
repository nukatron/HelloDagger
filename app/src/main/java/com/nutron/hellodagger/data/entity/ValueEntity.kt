package com.nutron.hellodagger.data.entity

import android.os.Parcel
import android.os.Parcelable


data class ValueEntity(val value: Int, val timeStamp: Long, var source: String? = null): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readLong(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(value)
        parcel.writeLong(timeStamp)
        parcel.writeString(source)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ValueEntity> {
        override fun createFromParcel(parcel: Parcel): ValueEntity {
            return ValueEntity(parcel)
        }

        override fun newArray(size: Int): Array<ValueEntity?> {
            return arrayOfNulls(size)
        }
    }


}