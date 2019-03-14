package com.skeleton.android.features.user

import android.arch.lifecycle.MutableLiveData
import com.skeleton.android.core.exception.Failure
import com.skeleton.android.core.extension.FirebaseAuthenticacionCallback
import com.skeleton.android.core.platform.BaseViewModel
import javax.inject.Inject

class UpdateStatusViewModel
@Inject constructor(private val updateStatus: UpdateStatus): BaseViewModel(){

    var trigger: MutableLiveData<Boolean> = MutableLiveData()

    fun updateStatus(status: Boolean){
        updateStatus(UpdateStatus.Params(status, object : FirebaseAuthenticacionCallback {
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