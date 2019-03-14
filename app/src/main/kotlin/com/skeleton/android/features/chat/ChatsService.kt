package com.skeleton.android.features.chat

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.skeleton.android.core.exception.Failure
import com.skeleton.android.core.extension.FirebaseAuthenticacionCallback
import com.skeleton.android.core.extension.FirebaseCallback
import com.skeleton.android.core.platform.ContextHandler
import com.skeleton.android.features.user.UserView
import java.util.*
import javax.inject.Inject




class ChatsService
@Inject constructor(contextHandler: ContextHandler, firebaseStore: FirebaseFirestore, firebaseAuth: FirebaseAuth) {

    val firebaseFirestore = firebaseStore
    val userRef = firebaseFirestore.collection("user")
    val chatRef = firebaseStore.collection("chat")

    /**
     * Fireabse Firestore.
     */
    fun getChats(load: FirebaseCallback) {

        userRef.document(FirebaseAuth.getInstance().currentUser!!.uid).collection("chat").addSnapshotListener { snapshot, firestoreExcepcion ->
            if (snapshot != null) {
                var mutableList = mutableListOf<Any>()
                for (i in 0 until snapshot.documents.size) {
                    mutableList.add(Chat(snapshot.documents[i]!!.get("chatID").toString(), snapshot.documents[i]!!.get("lastMessage").toString(), snapshot.documents[i]!!.get("with").toString(), snapshot.documents[i]!!.get("withID").toString()))
                }
                load.onResponse(mutableList)
            } else {
                load.onFailure(Failure.CustomError(666, firestoreExcepcion.toString()))
            }
        }
    }

    fun chatExists(chatID: String, load: FirebaseAuthenticacionCallback) {
        var boolean = false
        userRef.document(FirebaseAuth.getInstance().currentUser!!.uid).collection("chat").addSnapshotListener { snapshot, firebaseFirestoreException ->
            if (snapshot != null) {
                for (i in 0 until snapshot.documents.size) {
                    if (snapshot.documents[i]!!.get("chatID").toString().equals(chatID)) {
                        boolean = true
                    }
                }
                load.onSucces(boolean)
            } else {
                load.onError(Failure.CustomError(666, firebaseFirestoreException.toString()))
            }
        }
    }

    fun addChat(load: FirebaseAuthenticacionCallback) {
        chatRef.document().collection("messages").document().set(Message(FirebaseAuth.getInstance().uid!!, "Hola")).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                load.onSucces(true)
            } else {
                load.onError(Failure.CustomError(222, "error"))
            }
        }
    }

    fun messages(chatID: String, load: FirebaseCallback) {
        chatRef.document(chatID).collection("messages").orderBy("timestamp", Query.Direction.ASCENDING).addSnapshotListener { snapshot, firebaseFirestoreException ->
            if (snapshot != null) {
                var mutableList = mutableListOf<Any>()
                for (i in 0 until snapshot.documents.size) {
                    mutableList.add(Message(snapshot.documents[i].get("from").toString(), snapshot.documents[i].get("message").toString()))
                }
                load.onResponse(mutableList)
            } else {
                load.onFailure(Failure.CustomError(666, firebaseFirestoreException.toString()))
            }
        }
    }

    fun addMessage(message: String, chatID: String, with: String, withID: String) {
        if (chatID.isNotEmpty()) {
            val docData = HashMap<String, Any>()
            docData["timestamp"] = Timestamp.now()
            docData["from"] = FirebaseAuth.getInstance().uid.toString()
            docData["message"] = message
            chatRef.document(chatID).collection("messages").document().set(docData).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateMyUserChat(chatID, message, with, withID)
                    updateWithUserChat(chatID, message, with, withID)
                }
            }
        } else {
            val chatID = chatRef.document().id
            val docData = HashMap<String, Any>()
            docData["timestamp"] = Timestamp.now()
            docData["from"] = FirebaseAuth.getInstance().uid.toString()
            docData["message"] = message
            chatRef.document(chatID).collection("messages").document().set(docData).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateMyUserChat(chatID, message, with, withID)
                    updateWithUserChat(chatID, message, with, withID)
                }
            }
        }
    }

    fun updateMyUserChat(chatID: String, message: String, with: String, withID: String) {

        val updateChat = HashMap<String, Any>()
        updateChat["from"] = FirebaseAuth.getInstance().uid.toString()
        updateChat["lastMessage"] = message
        updateChat["chatID"] = chatID

        userRef.document(FirebaseAuth.getInstance().currentUser!!.uid).collection("chat").get().addOnCompleteListener { task ->

            if (task.isSuccessful) {
                if (task.result.size() != 0) {
                    for (i in 0 until task.result.size()) {
                        if (task.result.documents[i]!!.get("chatID").toString().equals(chatID)) {
                            val documentID = task.result.documents[i].id
                            updateChat["withID"] = task.result.documents[i]!!.get("withID").toString()
                            updateChat["with"] = task.result.documents[i]!!.get("with").toString()

                            userRef.document(FirebaseAuth.getInstance().currentUser!!.uid).collection("chat").document(documentID).set(updateChat)
                        }
                    }
                } else {
                    updateChat["withID"] = withID
                    updateChat["with"] = with
                    userRef.document(FirebaseAuth.getInstance().currentUser!!.uid).collection("chat").document().set(updateChat)
                }
            }
        }
    }

    fun updateWithUserChat(chatID: String, message: String, with: String, withID: String) {

        val updateChat = HashMap<String, Any>()
        updateChat["from"] = FirebaseAuth.getInstance().uid.toString()
        updateChat["lastMessage"] = message
        updateChat["chatID"] = chatID

        userRef.document(withID).collection("chat").get().addOnCompleteListener { task ->

            if (task.isSuccessful) {
                if (task.result.size() != 0) {
                    for (i in 0 until task.result.size()) {
                        if (task.result.documents[i]!!.get("chatID").toString().equals(chatID)) {
                            val documentID = task.result.documents[i].id
                            updateChat["withID"] = task.result.documents[i]!!.get("withID").toString()
                            updateChat["with"] = task.result.documents[i]!!.get("with").toString()

                            userRef.document(withID).collection("chat").document(documentID).set(updateChat)
                        }
                    }
                } else {
                    updateChat["withID"] = withID
                    updateChat["with"] = with
                    userRef.document(withID).collection("chat").document().set(updateChat)
                }
            }
        }
    }
}