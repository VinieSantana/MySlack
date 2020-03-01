package com.vinisantana.slack.features.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinisantana.slack.R
import com.vinisantana.slack.model.Message
import kotlinx.android.synthetic.main.row_receiver_message.view.*

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private var items: List<Message>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_receiver_message, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = items?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items?.let { holder.bind(it[position]) }
    }

    fun updateItems(items: List<Message>?) {
        this.items = items
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(
            message: Message
        ) {
            itemView.tvReceiver.text = message.message
        }
    }
}
