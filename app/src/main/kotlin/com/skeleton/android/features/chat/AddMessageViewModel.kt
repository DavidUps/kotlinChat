package com.skeleton.android.features.chat

import com.skeleton.android.core.exception.Failure
import com.skeleton.android.core.extension.FirebaseAuthenticacionCallback
import com.skeleton.android.core.platform.BaseViewModel
import com.skeleton.android.features.user.UserView
import javax.inject.Inject

class AddMessageViewModel
@Inject constructor(private val addMessage: AddMessage): BaseViewModel(){

    fun addMessage(message: String, chatID: String, with: String, withID: String){
        addMessage(AddMessage.Params(message, chatID, with, withID, object: FirebaseAuthenticacionCallback{
            override fun onSucces(boolean: Boolean) {

            }

            override fun onError(failure: Failure) {
                handleFailure(failure)
            }

        }))
    }
}