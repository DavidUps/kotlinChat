package com.skeleton.android.core.extension

import com.skeleton.android.core.exception.Failure
import com.skeleton.android.features.user.User

interface FirebaseCallback {
    fun onResponse(response: MutableList<Any>)
    fun onFailure(failure: Failure)
}