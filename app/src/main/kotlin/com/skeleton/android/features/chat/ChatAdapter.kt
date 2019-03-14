package com.skeleton.android.features.chat

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.skeleton.android.R
import com.skeleton.android.core.extension.inflate
import kotlinx.android.synthetic.main.recyclerview_chat_list.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class ChatAdapter
@Inject constructor(): RecyclerView.Adapter<ChatAdapter.ViewHolder>(){

    internal var collection: List<ChatView> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (ChatView) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.recyclerview_chat_list))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(collection[position], clickListener)
    }

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(chatView: ChatView, clickListener: (ChatView) -> Unit) {
            if (chatView!= null) {
                itemView.tvName.text = (chatView.with)
                itemView.tvLastMessage.text = (chatView.lastMessage)
                itemView.setOnClickListener {
                    clickListener(chatView)
                }
            }
        }
    }
}