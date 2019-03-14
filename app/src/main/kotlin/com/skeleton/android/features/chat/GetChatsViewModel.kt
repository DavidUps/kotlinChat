package com.skeleton.android.features.chat

import android.arch.lifecycle.MutableLiveData
import com.skeleton.android.core.exception.Failure
import com.skeleton.android.core.extension.FirebaseCallback
import com.skeleton.android.core.platform.BaseViewModel
import javax.inject.Inject

class GetChatsViewModel
@Inject constructor(private val getChats: GetChats) : BaseViewModel() {

    var chats: MutableLiveData<List<ChatView>> = MutableLiveData()

    fun chats() {
        getChats(GetChats.Params(object : FirebaseCallback {
            override fun onResponse(response: MutableList<Any>) {
                handleGetUser(response)
            }

            override fun onFailure(failure: Failure) {
                handleFailure(failure)
            }
        }))
    }

    private fun handleGetUser(chats: MutableList<Any>) {
        this.chats.value = chats.map {
            it as Chat
            it.toChatView()
        }
    }
}