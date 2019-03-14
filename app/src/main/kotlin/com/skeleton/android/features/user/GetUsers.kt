package com.skeleton.android.features.user

import com.skeleton.android.core.extension.FirebaseCallback
import com.skeleton.android.core.interactor.UseCaseWithout
import javax.inject.Inject

class GetUsers
@Inject constructor(private val userRepository: UserRepository) : UseCaseWithout<GetUsers.Params>() {
    override suspend fun run(params: Params) {
        userRepository.users(params.response)
    }

    data class Params(val response: FirebaseCallback)
}
