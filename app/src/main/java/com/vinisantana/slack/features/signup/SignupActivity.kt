package com.vinisantana.slack.features.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.vinisantana.slack.R
import com.vinisantana.slack.features.login.MainActivity.Companion.LOGIN_EMAIL
import com.vinisantana.slack.features.login.MainActivity.Companion.LOGIN_PASSWORD

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val intent = intent.extras
        val email = intent?.getString(LOGIN_EMAIL)
        val password = intent?.getString(LOGIN_PASSWORD)

    }
}
