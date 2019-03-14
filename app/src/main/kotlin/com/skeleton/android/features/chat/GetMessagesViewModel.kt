package com.skeleton.android.features.chat

import android.arch.lifecycle.MutableLiveData
import com.skeleton.android.core.exception.Failure
import com.skeleton.android.core.extension.FirebaseCallback
import com.skeleton.android.core.platform.BaseViewModel
import javax.inject.Inject

class GetMessagesViewModel
@Inject constructor(private val getMessages: GetMessages): BaseViewModel(){

    var messageList: MutableLiveData<List<MessageView>> = MutableLiveData()

    fun getMessages(chatID: String){
        getMessages(GetMessages.Params(chatID, object: FirebaseCallback{
            override fun onResponse(response: MutableList<Any>) {
                handleGetMessages(response)
            }

            override fun onFailure(failure: Failure) {
                handleFailure(failure)
            }

        }))
    }

    private fun handleGetMessages(response: MutableList<Any>) {
        this.messageList.value = response.map {
            it as Message
            it.toMessageView()
        }
    }
}