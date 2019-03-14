package com.skeleton.android.features.chat

import com.skeleton.android.core.extension.empty

data class Chat (val chatID: String, val lastMessage: String, val with: String, val withID: String) {
    companion object {
        fun empty() = Chat(String.empty(), String.empty(), String.empty(), String.empty())
    }

    fun toChatView(): ChatView{
        return ChatView(chatID, lastMessage, with, withID)
    }
}