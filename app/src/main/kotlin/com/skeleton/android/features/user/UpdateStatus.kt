package com.skeleton.android.features.user

import com.skeleton.android.core.extension.FirebaseAuthenticacionCallback
import com.skeleton.android.core.interactor.UseCaseWithout
import javax.inject.Inject

class UpdateStatus
@Inject constructor(private val userRepository: UserRepository): UseCaseWithout<UpdateStatus.Params>(){
    override suspend fun run(params: Params) {
        userRepository.updateStatus(params.status, params.firebaseAuthenticacionCallback)
    }

    class Params(val status: Boolean, val firebaseAuthenticacionCallback: FirebaseAuthenticacionCallback)
}