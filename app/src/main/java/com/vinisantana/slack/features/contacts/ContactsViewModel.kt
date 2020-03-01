package com.vinisantana.slack.features.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vinisantana.slack.model.User
import com.vinisantana.slack.repository.AuthRepository
import com.vinisantana.slack.repository.UserRepository

class ContactsViewModel : ViewModel() {

    private val usersData : LiveData<List<User>>
    private val userRepository = UserRepository()
    private val authRepository = AuthRepository()

    init {
        usersData = userRepository.users()
    }

    fun observerUsers() = usersData
    fun currentUser() = authRepository.currentUser()

}