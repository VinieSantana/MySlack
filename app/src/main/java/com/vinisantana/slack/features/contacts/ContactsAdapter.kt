package com.vinisantana.slack.features.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.*
import com.bumptech.glide.request.RequestOptions
import com.vinisantana.slack.R
import com.vinisantana.slack.model.User
import kotlinx.android.synthetic.main.row_contact.view.*

class ContactsAdapter(private val callback: ContactsCallback? = null) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private var items: List<User>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_contact, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = items?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items?.let { holder.bind(it[position], callback) }
    }

    fun updataItems(items: List<User>?) {
        this.items = items
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

     fun bind(user: User, callback:ContactsCallback?) {
           with(itemView.context)
               .load(user.photoUrl)
               .placeholder(R.drawable.ic_avatar_placeholder)
               .apply(RequestOptions.circleCropTransform())
               .into(itemView.imgUser)

           itemView.tvName.text = user.name
           itemView.tvLastMessage.text = ""
           itemView.setOnClickListener {
               if (callback != null) {
                   callback.onItemClick(user)
               }
           }
        }
    }

    interface ContactsCallback {
        fun onItemClick(user: User)
    }

}
