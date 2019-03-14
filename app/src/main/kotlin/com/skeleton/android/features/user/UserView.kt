package com.skeleton.android.features.user

import android.os.Parcel
import com.skeleton.android.core.platform.KParcelable
import com.skeleton.android.core.platform.parcelableCreator
import com.skeleton.android.core.platform.writeBoolean

data class UserView(val userID: String, val name: String, val status: Boolean): KParcelable{

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::UserView)
    }

    constructor(parcel: Parcel) : this(
            parcel.toString(),
            parcel.toString(),
            parcel as Boolean
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeString(userID)
            writeString(name)
            writeBoolean(status)
        }
    }
}