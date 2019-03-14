package com.skeleton.android.features.user

import com.skeleton.android.core.extension.FirebaseAuthenticacionCallback
import com.skeleton.android.core.interactor.UseCaseWithout
import javax.inject.Inject

class DoLogin
@Inject constructor(private val autenticationRepository: AuthenticationRepository): UseCaseWithout<DoLogin.Params>(){
    override suspend fun run(params: Params) {
        autenticationRepository.doLogin(params.authentication, params.firebaseAuthenticacionCallback)
    }

    class Params(val authentication: Authentication, val firebaseAuthenticacionCallback: FirebaseAuthenticacionCallback)
}