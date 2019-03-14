package com.skeleton.android.features.chat

import android.os.Parcel
import com.skeleton.android.core.platform.KParcelable
import com.skeleton.android.core.platform.parcelableCreator
import com.skeleton.android.core.platform.writeBoolean

data class ChatView(val chatID: String, val lastMessage: String, val with: String, val withID: String): KParcelable {

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::ChatView)
    }

    constructor(parcel: Parcel) : this(
            parcel.toString(),
            parcel.toString(),
            parcel.toString(),
            parcel.toString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeString(chatID)
            writeString(lastMessage)
            writeString(with)
            writeString(withID)
        }
    }
}