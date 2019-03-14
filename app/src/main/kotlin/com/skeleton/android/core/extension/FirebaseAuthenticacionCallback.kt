package com.skeleton.android.core.extension

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.skeleton.android.core.exception.Failure

interface FirebaseAuthenticacionCallback {
    fun onSucces(boolean: Boolean)
    fun onError(failure: Failure)
}