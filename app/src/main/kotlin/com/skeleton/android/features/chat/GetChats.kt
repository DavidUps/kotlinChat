package com.skeleton.android.features.chat

import com.skeleton.android.core.extension.FirebaseCallback
import com.skeleton.android.core.interactor.UseCaseWithout
import javax.inject.Inject

class GetChats
@Inject constructor(private val chatsRepository: ChatsRepository) : UseCaseWithout<GetChats.Params>() {
    override suspend fun run(params: Params) {
        chatsRepository.chats(params.response)
    }

    data class Params(val response: FirebaseCallback)
}
