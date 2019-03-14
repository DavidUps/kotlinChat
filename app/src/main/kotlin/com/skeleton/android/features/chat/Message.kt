package com.skeleton.android.features.chat

import com.google.firebase.Timestamp
import com.skeleton.android.core.extension.empty

data class Message (val from: String, val message: String) {
    companion object {
        fun empty() = Message(String.empty(), String.empty())
    }

    fun toMessageView(): MessageView{
        return MessageView(from, message)
    }
}