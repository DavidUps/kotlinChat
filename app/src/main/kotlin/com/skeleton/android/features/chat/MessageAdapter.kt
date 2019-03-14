package com.skeleton.android.features.chat

import android.opengl.Visibility
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.skeleton.android.R
import com.skeleton.android.core.extension.inflate
import kotlinx.android.synthetic.main.recyclerview_message.view.*
import javax.inject.Inject
import kotlin.properties.Delegates


class MessageAdapter
@Inject constructor(): RecyclerView.Adapter<MessageAdapter.ViewHolder>(){

    internal var collection: List<MessageView> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (ChatView) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.recyclerview_message))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(collection[position])
    }

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(messageView: MessageView) {
            if (messageView!= null) {
                if (messageView.from.equals(FirebaseAuth.getInstance().uid)){
                    itemView.tvMessage.text = (messageView.message)
                    itemView.tvMessage.visibility = View.VISIBLE
                } else{
                    itemView.tvWithMessage.text = (messageView.message)
                    itemView.tvWithMessage.visibility = View.VISIBLE
                }
            }
        }
    }
}