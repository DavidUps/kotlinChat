package com.skeleton.android.features.chat

import com.skeleton.android.core.extension.FirebaseCallback
import com.skeleton.android.core.interactor.UseCaseWithout
import com.skeleton.android.core.platform.BaseFragment
import javax.inject.Inject

class GetMessages
@Inject constructor(private val chatsRepository: ChatsRepository): UseCaseWithout<GetMessages.Params>() {
    override suspend fun run(params: GetMessages.Params) {
        chatsRepository.messages(params.chatID, params.load)
    }

    class Params(val chatID: String, val load: FirebaseCallback)
}