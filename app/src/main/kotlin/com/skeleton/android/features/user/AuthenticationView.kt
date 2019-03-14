package com.skeleton.android.features.user

import android.os.Parcel
import com.skeleton.android.core.platform.KParcelable
import com.skeleton.android.core.platform.parcelableCreator

data class AuthenticationView(val email: String, val password: String) : KParcelable {

    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::AuthenticationView)
    }

    constructor(parcel: Parcel) : this(
            parcel.toString(),
            parcel.toString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest){
            writeString(email)
            writeString(password)
        }
    }
}