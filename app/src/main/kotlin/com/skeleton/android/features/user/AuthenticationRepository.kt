package com.skeleton.android.features.user

import com.skeleton.android.core.exception.Failure
import com.skeleton.android.core.extension.FirebaseAuthenticacionCallback
import com.skeleton.android.core.platform.NetworkHandler
import javax.inject.Inject

interface AuthenticationRepository{

    fun doLogin(authentication: Authentication, firebaseAuthenticacionCallback: FirebaseAuthenticacionCallback)

    class Network
    @Inject constructor(private val networkHandler: NetworkHandler,
                        private val service: UserService): AuthenticationRepository {
        override fun doLogin(authentication: Authentication, firebaseAuthenticacionCallback: FirebaseAuthenticacionCallback) {
            return when (networkHandler.isConnected) {
                true -> service.login(authentication, firebaseAuthenticacionCallback)
                else -> firebaseAuthenticacionCallback.onError(Failure.NetworkConnection())
            }
        }
    }
}