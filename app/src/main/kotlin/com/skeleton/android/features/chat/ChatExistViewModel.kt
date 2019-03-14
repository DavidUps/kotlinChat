package com.skeleton.android.features.chat

import android.arch.lifecycle.MutableLiveData
import com.skeleton.android.core.exception.Failure
import com.skeleton.android.core.extension.FirebaseAuthenticacionCallback
import com.skeleton.android.core.platform.BaseViewModel
import javax.inject.Inject

class ChatExistViewModel
@Inject constructor(private val chatExist: ChatExist): BaseViewModel(){

    fun chatExist(chatID: String, load: FirebaseAuthenticacionCallback){
        chatExist(ChatExist.Params(chatID, object : FirebaseAuthenticacionCallback{
            override fun onSucces(boolean: Boolean) {
                load.onSucces(boolean)
            }

            override fun onError(failure: Failure) {
            }
        }))
    }
}