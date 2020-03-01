package com.vinisantana.slack.features.myprofile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.vinisantana.slack.R
import com.vinisantana.slack.features.base.BaseActivity
import com.vinisantana.slack.features.login.MainActivity
import kotlinx.android.synthetic.main.activity_contacts.toolbarInclude
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.row_contact.imgUser
import kotlinx.android.synthetic.main.row_contact.tvName

class MyProfileActivity : BaseActivity() {


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_my_profile)
            initActionbar()
            initUI()

            btnLogout.setOnClickListener {
                auth.signOut()
                    auth.addAuthStateListener {
                        if(auth.currentUser == null){
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }
            }
        }

        private fun initUI() {

            auth.currentUser?.let { user ->
                tvName.text = user.displayName
                tvEmail.text = user.email
                Glide.with(this)
                    .load(user.photoUrl)
                    .placeholder(R.drawable.ic_avatar_placeholder)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgUser)
            }
        }


        private fun initActionbar() {
            setSupportActionBar(toolbarInclude.toolbar)
            toolbarInclude.tvToolbarTitle.text = getString(R.string.my_profile)
        }
    }


