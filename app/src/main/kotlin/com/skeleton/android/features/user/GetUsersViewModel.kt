package com.skeleton.android.features.user

import android.arch.lifecycle.MutableLiveData
import com.skeleton.android.core.exception.Failure
import com.skeleton.android.core.extension.FirebaseCallback
import com.skeleton.android.core.platform.BaseViewModel
import javax.inject.Inject

class GetUsersViewModel
@Inject constructor(private val getUsers: GetUsers) : BaseViewModel() {

    var users: MutableLiveData<List<UserView>> = MutableLiveData()

    fun users() {
        getUsers(GetUsers.Params(object : FirebaseCallback {
            override fun onResponse(response: MutableList<Any>) {
                handleGetUser(response)
            }

            override fun onFailure(failure: Failure) {
                handleFailure(failure)
            }
        }))
    }

    private fun handleGetUser(user: MutableList<Any>) {
        this.users.value = user.map {
            it as User
            it.toUserView()
        }
    }
}