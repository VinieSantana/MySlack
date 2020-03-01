package com.vinisantana.slack.features.contacts

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.vinisantana.slack.R
import com.vinisantana.slack.features.base.BaseActivity
import com.vinisantana.slack.features.chat.ChatActivity
import com.vinisantana.slack.features.myprofile.MyProfileActivity
import com.vinisantana.slack.model.User
import kotlinx.android.synthetic.main.activity_contacts.*
import kotlinx.android.synthetic.main.custom_toolbar.view.*

class ContactsActivity :  BaseActivity(), ContactsAdapter.ContactsCallback  {

    val adapter = ContactsAdapter(this)

    val viewModel by lazy {
        ViewModelProviders.of(this).get(ContactsViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        initActionbar()
        initUI()
        initObservables()
    }

    private fun initUI() {
        rViewContacts.layoutManager = LinearLayoutManager(this)
        rViewContacts.adapter = adapter
    }


    private fun initActionbar() {
        setSupportActionBar(toolbarInclude.toolbar)

        toolbarInclude.tvToolbarTitle.text = getString(R.string.contacts)
        toolbarInclude.imgToolbarUser.setOnClickListener {
            startActivity(Intent(this, MyProfileActivity::class.java))
        }

        viewModel.currentUser()?.let { user ->
            Glide.with(this)
                .load(user.photoUrl)
                .placeholder(R.drawable.ic_avatar_placeholder)
                .apply(RequestOptions.circleCropTransform())
                .into(toolbarInclude.imgToolbarUser)
        }
    }

    override fun onItemClick(user: User) {
        val intent = Intent(this, ChatActivity::class.java).apply {
            putExtra("userName", user.name)
            putExtra("userId", user.id)
        }
        startActivity(intent)
    }

    private fun initObservables() {
        viewModel.observerUsers().observe(this, Observer {
            adapter.updataItems(it)
        })
    }
}
