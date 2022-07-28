package io.aethibo.fork.ui.feed.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import by.kirich1409.viewbindingdelegate.viewBinding
import io.aethibo.data.utils.Resource
import io.aethibo.domain.User
import io.aethibo.domain.response.EventsResponse
import io.aethibo.fork.R
import io.aethibo.fork.databinding.FragmentFeedBinding
import io.aethibo.fork.ui.auth.utils.snackBar
import io.aethibo.fork.ui.feed.adapter.FeedAdapter
import io.aethibo.fork.ui.feed.viewmodel.FeedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedFragment : Fragment(R.layout.fragment_feed) {

    private val binding: FragmentFeedBinding by viewBinding()
    private val viewModel: FeedViewModel by viewModel()
    private val feedAdapter: FeedAdapter by lazy { FeedAdapter() }

    companion object {
        fun newInstance() = FeedFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserInformation()

        subscribeToObservers()
        setupAdapterClickListeners()
    }

    private fun setupAdapterClickListeners() {
        feedAdapter.setOnEventItemClickListener { event ->
            snackBar("Clicked: ${event.type}")
        }
    }

    private fun setupAdapter(data: List<EventsResponse>) {
        feedAdapter.submitList(data)
        binding.rvEventsList.adapter = feedAdapter
    }

    private fun subscribeToObservers() {
        viewModel.eventsStatus.asLiveData()
            .observe(viewLifecycleOwner) { resource: Resource<List<EventsResponse>> ->
                when (resource) {
                    is Resource.Loading -> binding.pbEvents.isVisible = true
                    is Resource.Success -> {
                        binding.pbEvents.isVisible = false

                        setupAdapter(resource.data ?: emptyList())
                    }
                    is Resource.Failure -> {
                        binding.pbEvents.isVisible = false
                        snackBar("Error: ${resource.message ?: "Unknown error occurred"}")
                    }
                    else -> {}
                }
            }

        viewModel.userStatus.asLiveData().observe(viewLifecycleOwner) { resource: Resource<User> ->
            when (resource) {
                is Resource.Loading -> binding.pbEvents.isVisible = true
                is Resource.Success -> {
                    binding.pbEvents.isVisible = false

                    val result: User = resource.data as User
                    viewModel.getEvents(result.login)
                }
                is Resource.Failure -> {
                    binding.pbEvents.isVisible = false
                    snackBar("Error: ${resource.message ?: "Unknown error occurred"}")
                }
                else -> {}
            }
        }
    }
}