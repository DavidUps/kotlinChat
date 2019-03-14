package com.skeleton.android.features.chat

import com.skeleton.android.core.extension.FirebaseAuthenticacionCallback
import com.skeleton.android.core.interactor.UseCaseWithout
import javax.inject.Inject

class AddChat
@Inject constructor(private val chatsRepository: ChatsRepository) : UseCaseWithout<AddChat.Params>() {
    override suspend fun run(params: Params) {
        chatsRepository.addChat(params.firebaseAuthenticacionCallback)
    }


    data class Params(val firebaseAuthenticacionCallback: FirebaseAuthenticacionCallback)
}