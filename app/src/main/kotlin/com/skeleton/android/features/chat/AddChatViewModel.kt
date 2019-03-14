package com.skeleton.android.features.chat

import android.arch.lifecycle.MutableLiveData
import com.skeleton.android.core.exception.Failure
import com.skeleton.android.core.extension.FirebaseAuthenticacionCallback
import com.skeleton.android.core.platform.BaseViewModel
import javax.inject.Inject

class AddChatViewModel
@Inject constructor(private val addChat: AddChat): BaseViewModel(){

    var trigger: MutableLiveData<Boolean> = MutableLiveData()

    fun addChat(){
        addChat(AddChat.Params(object : FirebaseAuthenticacionCallback{
            override fun onSucces(boolean: Boolean) {
                handleAddChat(boolean)
            }

            override fun onError(failure: Failure) {
            }

        }))
    }

    private fun handleAddChat(boolean: Boolean) {
        this.trigger.value = boolean
    }
}