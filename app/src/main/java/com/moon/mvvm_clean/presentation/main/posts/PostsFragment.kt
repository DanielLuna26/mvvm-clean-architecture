package com.moon.mvvm_clean.presentation.main.posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moon.mvvm_clean.R
import com.moon.mvvm_clean.databinding.FragmentPostsBinding
import com.moon.mvvm_clean.domain.Resource
import com.moon.mvvm_clean.presentation.recycler_view.adapters.PostsRecyclerViewAdapter
import com.moon.mvvm_clean.utils.delegate.viewBinding
import com.moon.mvvm_clean.utils.handleApiErrors
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostsFragment : Fragment(R.layout.fragment_posts) {

    private val viewModel: PostsViewModel by viewModels()

    private val binding by viewBinding(FragmentPostsBinding::bind)

    private lateinit var postAdapter: PostsRecyclerViewAdapter
    private var pastVisibleItems: Int = 0
    private  var visibleItems: Int = 0
    private var totalItemCount: Int = 0

    private var page: Int = 1

    private var loading = true

    private lateinit var linearManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postAdapter = PostsRecyclerViewAdapter()

        linearManager = LinearLayoutManager(requireContext())

        binding.postsRvItems.apply {
            setHasFixedSize(true)
            layoutManager = linearManager
            adapter = postAdapter
        }

        runCollectors()

        runObservers()

        binding.postsRvItems.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    visibleItems = linearManager.childCount
                    totalItemCount = linearManager.itemCount
                    pastVisibleItems = linearManager.findFirstVisibleItemPosition()

                    if (loading) {
                        if ((visibleItems + pastVisibleItems) >= totalItemCount) {
                            loading = false
                            page++
                            viewModel.getPosts(page)
                            loading = true
                        }
                    }
                }
            }
        })
    }

    private fun runCollectors() = viewLifecycleOwner.lifecycleScope.launch {
        viewModel.result.collect {
            val result = it ?: return@collect

            when (result) {
                is Resource.Success -> viewModel.addItems(result)
                is Resource.Failure -> handleApiErrors(result) { viewModel.getPosts(page) }
            }
        }

    }

    private fun runObservers() {
        viewModel.posts.observe(viewLifecycleOwner) {
            postAdapter.items = it
        }
    }

}