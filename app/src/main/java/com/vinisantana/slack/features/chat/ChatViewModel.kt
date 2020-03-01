package com.vinisantana.slack.features.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vinisantana.slack.model.Message
import com.vinisantana.slack.model.User
import com.vinisantana.slack.repository.AuthRepository
import com.vinisantana.slack.repository.ChatRepository
import com.vinisantana.slack.repository.UserRepository

class ChatViewModel : ViewModel() {

    private val messageData : LiveData<List<Message>>
    private val chatRepository = ChatRepository()
   // private val authRepository = AuthRepository()

    init {
        messageData = chatRepository.getMessage()
    }

    fun observerMessages() = messageData
  //  fun currentUser() = authRepository.currentUser()

}