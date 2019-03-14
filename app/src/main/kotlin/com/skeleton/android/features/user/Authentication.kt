package com.skeleton.android.features.user

import com.skeleton.android.core.extension.empty

data class Authentication (val email: String, val password: String) {

    companion object {
        fun empty() = Authentication(String.empty(), String.empty())
    }

    fun toLoginView(): AuthenticationView {
        return AuthenticationView(email, password)
    }
}