package com.skeleton.android.features.chat

import com.skeleton.android.core.exception.Failure
import com.skeleton.android.core.extension.FirebaseAuthenticacionCallback
import com.skeleton.android.core.extension.FirebaseCallback
import com.skeleton.android.core.platform.NetworkHandler
import com.skeleton.android.features.user.UserView
import javax.inject.Inject

interface ChatsRepository {

    fun chats(load: FirebaseCallback)
    fun chatExist(chatID: String, load: FirebaseAuthenticacionCallback)
    fun addChat(load: FirebaseAuthenticacionCallback)
    fun messages(chatID: String, load: FirebaseCallback)
    fun addMessage(message: String, chatID: String, with: String, withID: String, load: FirebaseAuthenticacionCallback)

    class Network
    @Inject constructor(private val networkHandler: NetworkHandler,
                        private val service: ChatsService) : ChatsRepository {

        override fun chats(load: FirebaseCallback) {
            return when (networkHandler.isConnected) {
                true -> {
                    service.getChats(load)
                }
                false, null -> load.onFailure(Failure.NetworkConnection())
            }
        }

        override fun chatExist(chatID : String, load: FirebaseAuthenticacionCallback) {
            return when (networkHandler.isConnected) {
                true -> service.chatExists(chatID, load)
                false, null -> load.onError(Failure.NetworkConnection())
            }
        }

        override fun addChat(load: FirebaseAuthenticacionCallback) {
            return when (networkHandler.isConnected) {
                true -> service.addChat(load)
                false, null -> load.onError(Failure.NetworkConnection())
            }
        }

        override fun messages(chatID: String, load: FirebaseCallback) {
            return when (networkHandler.isConnected) {
                true -> service.messages(chatID,load)
                false, null -> load.onFailure(Failure.NetworkConnection())
            }
        }

        override fun addMessage(message: String, chatID: String, with: String, withID: String, load: FirebaseAuthenticacionCallback) {
            return when (networkHandler.isConnected){
                true -> service.addMessage(message, chatID, with, withID)
                false, null -> load.onError(Failure.NetworkConnection())
            }
        }
    }
}