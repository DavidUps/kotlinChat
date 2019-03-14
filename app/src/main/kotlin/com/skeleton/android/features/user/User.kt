package com.skeleton.android.features.user

import com.google.firebase.firestore.QueryDocumentSnapshot
import com.skeleton.android.core.extension.empty

data class User(val userID: String,val name: String, val status: Boolean){
    companion object {
        fun empty() = User(String.empty(), String.empty(), false)
    }

    fun toUserView(): UserView{
        return UserView(userID, name, status)
    }
}