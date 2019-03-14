package com.skeleton.android.features.chat

import com.skeleton.android.core.extension.FirebaseAuthenticacionCallback
import com.skeleton.android.core.interactor.UseCaseWithout
import javax.inject.Inject

class ChatExist
@Inject constructor(private val chatsRepository: ChatsRepository) : UseCaseWithout<ChatExist.Params>() {
    override suspend fun run(params: Params) {
        chatsRepository.chatExist(params.chatID, params.firebaseAuthenticacionCallback)
    }


    data class Params(val chatID: String, val firebaseAuthenticacionCallback: FirebaseAuthenticacionCallback)
}