package com.moon.mvvm_clean.presentation.recycler_view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moon.mvvm_clean.databinding.ItemPostBinding
import com.moon.mvvm_clean.domain.models.Post
import com.moon.mvvm_clean.presentation.recycler_view.diff_callbacks.PostDiffCallbacks
import com.moon.mvvm_clean.presentation.recycler_view.view_holders.PostsRecyclerViewHolder

class PostsRecyclerViewAdapter : RecyclerView.Adapter<PostsRecyclerViewHolder>() {

    var items = listOf<Post>()
        set(value) {
            val diffCallback = DiffUtil.calculateDiff(PostDiffCallbacks(field, value))
            field = value
            diffCallback.dispatchUpdatesTo(this)
        }

    var itemClickListener: ((item: Post)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsRecyclerViewHolder {
        return PostsRecyclerViewHolder(
            ItemPostBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: PostsRecyclerViewHolder, position: Int) {
        holder.itemClickListener = itemClickListener
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}