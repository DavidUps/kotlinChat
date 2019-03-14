package com.skeleton.android.features.chat

import android.os.Parcel
import com.google.firebase.Timestamp
import com.skeleton.android.core.platform.KParcelable
import com.skeleton.android.core.platform.parcelableCreator

data class MessageView(val from: String, val message: String): KParcelable {

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::MessageView)
    }

    constructor(parcel: Parcel) : this(
            parcel.toString(),
            parcel.toString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeString(from)
            writeString(message)
        }
    }
}