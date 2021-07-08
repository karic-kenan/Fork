/*
 * Created by Karic Kenan on 21.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.fork.ui.detail.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import by.kirich1409.viewbindingdelegate.viewBinding
import io.aethibo.data.utils.Resource
import io.aethibo.domain.Repository
import io.aethibo.domain.response.RepositoryEventsResponse
import io.aethibo.fork.R
import io.aethibo.fork.databinding.FragmentRepositoryDetailBinding
import io.aethibo.fork.framework.domain.RepositoryDetailRequest
import io.aethibo.fork.ui.auth.utils.snackBar
import io.aethibo.fork.ui.detail.adapter.RepositoryDetailAdapter
import io.aethibo.fork.ui.detail.viewmodel.DetailRepositoryViewModel
import io.aethibo.fork.ui.utils.formatLargeNumber
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailRepositoryFragment : Fragment(R.layout.fragment_repository_detail) {

    private val binding: FragmentRepositoryDetailBinding by viewBinding()
    private val viewModel: DetailRepositoryViewModel by viewModel()
    private val repositoryEventsAdapter: RepositoryDetailAdapter by lazy { RepositoryDetailAdapter() }

    private var repositoryDetail: RepositoryDetailRequest? = null

    companion object {
        fun newInstance(repositoryDetail: RepositoryDetailRequest) =
            DetailRepositoryFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("repositoryDetail", repositoryDetail)
                }
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        arguments?.getParcelable<RepositoryDetailRequest>("repositoryDetail")
            ?.let { repositoryDetail ->
                this.repositoryDetail = repositoryDetail
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repositoryDetail?.let {
            viewModel.getRepository(it.owner, it.repository)
            viewModel.getRepositoryEventsList(it.owner, it.repository, mapOf("per_page" to "10"))
        }

        subscribeToObservers()
        setupAdapterClickListeners()
    }

    private fun setupAdapterClickListeners() {
        repositoryEventsAdapter.setOnEventItemClickListener { repositoryEventsResponse ->
            snackBar("Clicked: ${repositoryEventsResponse.type}")
        }
    }

    private fun subscribeToObservers() {
        viewModel.repositoryStatus.asLiveData()
            .observe(viewLifecycleOwner) { resource: Resource<Repository> ->
                when (resource) {
                    is Resource.Loading -> binding.pbRepositoryDetail.isVisible = true
                    is Resource.Success -> {
                        binding.pbRepositoryDetail.isVisible = false

                        val result = resource.data as Repository
                        setupRepositoryUi(result)
                    }
                    is Resource.Failure -> {
                        binding.pbRepositoryDetail.isVisible = false
                        snackBar("Error: ${resource.message ?: "Unknown error occurred"}")
                    }
                }
            }

        viewModel.repositoryEventsStatus.asLiveData()
            .observe(viewLifecycleOwner) { resource: Resource<List<RepositoryEventsResponse>> ->
                when (resource) {
                    is Resource.Loading -> binding.pbRepositoryDetail.isVisible = true
                    is Resource.Success -> {
                        binding.pbRepositoryDetail.isVisible = false

                        val result = resource.data as List<RepositoryEventsResponse>
                        setupAdapter(result)
                    }
                    is Resource.Failure -> {
                        binding.pbRepositoryDetail.isVisible = false
                        snackBar("Error: ${resource.message ?: "Unknown error occurred"}")
                    }
                }
            }
    }

    private fun setupAdapter(events: List<RepositoryEventsResponse>) {
        repositoryEventsAdapter.submitList(events)
        binding.rvRepoDetailEventsList.adapter = repositoryEventsAdapter
    }

    private fun setupRepositoryUi(repository: Repository) {
        binding.tvRepoDetailTitle.text = repository.name
        binding.tvRepoDetailDescription.text = repository.description
        binding.tvRepoDetailOwner.text = getString(R.string.labelRepositoryOwner, repository.owner)
        binding.tvRepoDetailLanguage.text =
            getString(R.string.labelRepositoryLanguage, repository.language)
        binding.tvRepoDetailUpdated.text =
            getString(R.string.labelRepositoryUpdatedDate, repository.updatedAt)
        binding.tvRepoDetailForksCount.text =
            getString(R.string.labelRepositoryForks, formatLargeNumber(repository.forksCount))
        binding.tvRepoDetailWatchersCount.text =
            getString(R.string.labelRepositoryWatchers, formatLargeNumber(repository.watchersCount))
        binding.tvRepoDetailStargazersCount.text =
            getString(R.string.labelRepositoryStars, formatLargeNumber(repository.stargazersCount))
    }
}