package com.skeleton.android.features.user

import com.skeleton.android.core.exception.Failure
import com.skeleton.android.core.extension.FirebaseAuthenticacionCallback
import com.skeleton.android.core.extension.FirebaseCallback
import com.skeleton.android.core.platform.NetworkHandler
import javax.inject.Inject

interface UserRepository{

    fun users(load: FirebaseCallback)
    fun updateStatus(status: Boolean, firebaseAuthenticacionCallback: FirebaseAuthenticacionCallback)

    class Network
    @Inject constructor(private val networkHandler: NetworkHandler,
                        private val service: UserService): UserRepository{

        override fun users(load: FirebaseCallback){
            return when (networkHandler.isConnected) {
                true -> {
                    service.getUser(load)
                }
                false, null -> load.onFailure(Failure.NetworkConnection())
            }
        }

        override fun updateStatus(status: Boolean, firebaseAuthenticacionCallback: FirebaseAuthenticacionCallback) {
            return when (networkHandler.isConnected) {
                true -> {
                    service.updateStatus(status, firebaseAuthenticacionCallback)
                }
                else -> firebaseAuthenticacionCallback.onError(Failure.ServerError())
            }
        }
    }
}