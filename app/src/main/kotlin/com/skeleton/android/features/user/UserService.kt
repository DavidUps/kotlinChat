package com.skeleton.android.features.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.skeleton.android.core.exception.Failure
import com.skeleton.android.core.extension.FirebaseAuthenticacionCallback
import com.skeleton.android.core.extension.FirebaseCallback
import com.skeleton.android.core.platform.ContextHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserService
@Inject constructor(contextHandler: ContextHandler, firebaseStore: FirebaseFirestore, firebaseAuth: FirebaseAuth){

    var firebaseFirestore = firebaseStore
    var firebaseAuth = firebaseAuth
    val docRef = firebaseFirestore.collection("user")

    /**
     * Fireabse Firestore.
     */
    fun getUser(load: FirebaseCallback){
        docRef.addSnapshotListener { snapshot, firestoreExcepcion ->
            if (snapshot != null) {
                var mutableList = mutableListOf<Any>()
                for (i in 0 until snapshot.documents.size){
                    mutableList.add(User(snapshot.documents[i].id, snapshot.documents[i]!!.get("name").toString(), snapshot.documents[i]!!.get("status") as Boolean))
                }
                load.onResponse(mutableList)
            } else {
                load.onFailure(Failure.CustomError(666, firestoreExcepcion.toString()))
            }
        }
    }

    fun addUser(firebaseAuthenticacionCallback: FirebaseAuthenticacionCallback){
        docRef.document(FirebaseAuth.getInstance().currentUser!!.uid).set(User(FirebaseAuth.getInstance().currentUser!!.uid, FirebaseAuth.getInstance().currentUser!!.email!!, false)).addOnCompleteListener{task ->
            if (task.isSuccessful){
                firebaseAuthenticacionCallback.onSucces(true)
            } else{
                firebaseAuthenticacionCallback.onError(Failure.ServerError())
            }
        }
    }

    fun updateStatus(status: Boolean, firebaseAuthenticacionCallback: FirebaseAuthenticacionCallback){
        val userStatus = HashMap<String,Boolean>()
        userStatus["status"] = status
        docRef.document(FirebaseAuth.getInstance().currentUser!!.uid).set(userStatus as Map<String, Any>, SetOptions.merge()).addOnCompleteListener{task ->
            if (task.isSuccessful){
                firebaseAuthenticacionCallback.onSucces(true)
            }
        }
    }

    /**
     * Firebase Authentication.
     */
    fun login(authentication: Authentication, firebaseAuthenticacionCallback: FirebaseAuthenticacionCallback){
        firebaseAuth.signInWithEmailAndPassword(authentication.email, authentication.password).addOnCompleteListener{ task ->
            if (task.isSuccessful) {
                firebaseAuthenticacionCallback.onSucces(true)
            } else {
                registry(authentication, firebaseAuthenticacionCallback)
            }
        }
    }

    fun registry(authentication: Authentication, firebaseAuthenticacionCallback: FirebaseAuthenticacionCallback){
        firebaseAuth.createUserWithEmailAndPassword(authentication.email, authentication.password).addOnCompleteListener {task ->
            if (task.isSuccessful){
                addUser(firebaseAuthenticacionCallback)
            }else{
                firebaseAuthenticacionCallback.onSucces(false)
            }
        }
    }

}