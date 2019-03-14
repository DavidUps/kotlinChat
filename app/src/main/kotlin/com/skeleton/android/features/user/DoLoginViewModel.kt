package com.skeleton.android.features.user

import android.arch.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.skeleton.android.core.exception.Failure
import com.skeleton.android.core.extension.FirebaseAuthenticacionCallback
import com.skeleton.android.core.platform.BaseViewModel
import javax.inject.Inject

class DoLoginViewModel
@Inject constructor(private val doLogin: DoLogin): BaseViewModel(){

    var trigger: MutableLiveData<Boolean> = MutableLiveData()

    fun doLogin(authentication: Authentication){
        doLogin(DoLogin.Params(authentication, object : FirebaseAuthenticacionCallback {
            override fun onSucces(boolean: Boolean) {
                handleGetUser(boolean)
            }

            override fun onError(failure: Failure) {
                handleFailure(failure)
            }
        }))
    }

    private fun handleGetUser(boolean: Boolean) {
        this.trigger.value = boolean
    }
}