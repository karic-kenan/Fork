package io.aethibo.fork.ui.feed.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import by.kirich1409.viewbindingdelegate.viewBinding
import io.aethibo.data.utils.Resource
import io.aethibo.domain.response.EventsResponse
import io.aethibo.fork.R
import io.aethibo.fork.databinding.FragmentFeedBinding
import io.aethibo.fork.ui.auth.utils.snackBar
import io.aethibo.fork.ui.feed.adapter.FeedAdapter
import io.aethibo.fork.ui.feed.viewmodel.FeedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class FeedFragment : Fragment(R.layout.fragment_feed) {

    private val binding: FragmentFeedBinding by viewBinding()
    private val viewModel: FeedViewModel by viewModel()
    private val feedAdapter: FeedAdapter by lazy { FeedAdapter() }

    companion object {
        fun newInstance() = FeedFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // hardcoded for now, we'll save username in shared prefs
        viewModel.getEvents("primepixel")

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

                        setupAdapter(resource.data as List<EventsResponse> ?: emptyList())
                    }
                    is Resource.Failure -> {
                        binding.pbEvents.isVisible = false
                        Timber.e("Error: ${resource.message}")
                        snackBar("Error: ${resource.message ?: "Unknown error occurred"}")
                    }
                }
            }
    }
}