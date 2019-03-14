package com.skeleton.android.core.extension

import com.skeleton.android.core.exception.Failure

interface Call<T: Any> {
    fun execute(): Response<T>

    data class Response<T>(var isSuccessful: Boolean, var body: T?, var failure: Failure?)
}