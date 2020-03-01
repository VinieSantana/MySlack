package com.vinisantana.slack.features.chat

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.vinisantana.slack.R
import com.vinisantana.slack.features.base.BaseActivity
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_contacts.toolbarInclude
import kotlinx.android.synthetic.main.custom_chat_footer.view.*
import kotlinx.android.synthetic.main.custom_toolbar.view.*

class ChatActivity : BaseActivity(){

    val adapter = ChatAdapter()

    val viewModel by lazy {
        ViewModelProviders.of(this).get(ChatViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initActionbar()
        initUI()
        initObservables()
        footerInclude.btnSend.setOnClickListener {
            Log.d("MESSAGE", "EnVIOU!")
        }

    }

    private fun initUI() {
        rViewMessages.layoutManager = LinearLayoutManager(this)
        rViewMessages.adapter = adapter
    }
    private fun initActionbar() {
        setSupportActionBar(toolbarInclude.toolbar)
        toolbarInclude.tvToolbarTitle.text = intent.extras?.getString("userName")
    }

    private fun initObservables() {
        viewModel.observerMessages().observe(this, Observer {
            adapter.updateItems(it)
        })
    }
}
