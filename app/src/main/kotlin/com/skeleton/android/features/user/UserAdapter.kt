package com.skeleton.android.features.user

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.skeleton.android.R
import com.skeleton.android.core.extension.inflate
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class UserAdapter
@Inject constructor(): RecyclerView.Adapter<UserAdapter.ViewHolder>(){

    internal var collection: List<UserView> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (UserView) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.recyclerview_item))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(collection[position], clickListener)
    }

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(userView: UserView, clickListener: (UserView) -> Unit) {
            if (!userView.name.isNullOrEmpty()) {
                itemView.tvName.text = (userView.name)
                itemView.setOnClickListener {
                    clickListener(userView)
                }
            }
        }
    }
}