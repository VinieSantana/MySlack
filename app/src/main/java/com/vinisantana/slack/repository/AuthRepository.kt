package com.vinisantana.slack.repository

import com.google.firebase.auth.FirebaseAuth

class AuthRepository {
    fun currentUser() = FirebaseAuth.getInstance().currentUser
}