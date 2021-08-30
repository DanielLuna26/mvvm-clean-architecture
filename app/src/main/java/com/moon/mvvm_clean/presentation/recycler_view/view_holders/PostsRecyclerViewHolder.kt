package com.moon.mvvm_clean.presentation.recycler_view.view_holders

import androidx.recyclerview.widget.RecyclerView
import com.moon.mvvm_clean.databinding.ItemPostBinding
import com.moon.mvvm_clean.domain.models.Post

class PostsRecyclerViewHolder (
    private val binding: ItemPostBinding
) : RecyclerView.ViewHolder(binding.root) {
    var itemClickListener: ((item: Post)->Unit)? = null

    fun bind(post: Post) {
        binding.post = post
    }
}