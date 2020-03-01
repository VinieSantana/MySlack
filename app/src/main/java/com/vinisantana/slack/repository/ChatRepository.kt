package com.vinisantana.slack.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.vinisantana.slack.model.Message

class ChatRepository {

    fun getMessage(): LiveData<List<Message>> {
        val items = MutableLiveData<List<Message>>()

//        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        FirebaseFirestore.getInstance().collection("channels")
            .document("xyz")
            .collection("messages")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                querySnapshot?.let {
                    val messages = it.toObjects(Message::class.java)
                    Log.d("TESTE", messages.toString())
                    items.value = messages
                }
            }

        return items
    }

//    fun insertMessages( message : Message){
//
////        FirebaseFirestore.getInstance().collection("channel").
////            .document()
////            .set()
////            .addOnSuccessListener {
////                Log.d("ADD_USER", "Cadastro efetuado!")
////            }
////            .addOnFailureListener {
////                Log.e("ADD_USER", "Erro no cadastro", it)
////            }
//    }

    }

