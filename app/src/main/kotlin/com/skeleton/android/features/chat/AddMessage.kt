package com.skeleton.android.features.chat

import com.skeleton.android.core.extension.FirebaseAuthenticacionCallback
import com.skeleton.android.core.interactor.UseCaseWithout
import com.skeleton.android.features.user.UserView
import javax.inject.Inject

class AddMessage
@Inject constructor(private val chatsRepository: ChatsRepository): UseCaseWithout<AddMessage.Params>(){
    override suspend fun run(params: AddMessage.Params) {
        chatsRepository.addMessage(params.message, params.chatID, params.with, params.withID, params.firebaseAuthenticacionCallback)
    }

    class Params(val message: String, val chatID: String, val with: String, val withID: String, val firebaseAuthenticacionCallback: FirebaseAuthenticacionCallback)

}