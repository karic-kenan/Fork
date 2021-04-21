package io.aethibo.fork.ui.search.view

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pandora.bottomnavigator.BottomNavigator
import io.aethibo.data.utils.Resource
import io.aethibo.domain.Repository
import io.aethibo.fork.R
import io.aethibo.fork.databinding.FragmentSearchBinding
import io.aethibo.fork.framework.domain.RepositoryDetailRequest
import io.aethibo.fork.ui.auth.utils.snackBar
import io.aethibo.fork.ui.detail.view.DetailRepositoryFragment
import io.aethibo.fork.ui.search.adapter.SearchAdapter
import io.aethibo.fork.ui.search.viewmodel.SearchViewModel
import io.aethibo.fork.ui.utils.hideKeyboard
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class SearchFragment : Fragment(R.layout.fragment_search), SearchView.OnQueryTextListener {

    private val binding: FragmentSearchBinding by viewBinding()
    private val viewModel: SearchViewModel by viewModel()
    private val searchAdapter: SearchAdapter by lazy { SearchAdapter() }

    private lateinit var navigator: BottomNavigator
    private var searchJob: Job? = null

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigator = BottomNavigator.provide(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        subscribeToObservers()
        setupAdapterClickListener()
    }

    private fun setupAdapterClickListener() {
        searchAdapter.setOnSearchItemClickListener { repository ->
            val repositoryInfo = RepositoryDetailRequest(repository.owner, repository.name)
            navigator.addFragment(DetailRepositoryFragment.newInstance(repositoryInfo))
        }
    }

    private fun subscribeToObservers() {
        viewModel.repositoriesStatus.asLiveData()
            .observe(viewLifecycleOwner) { resource: Resource<List<Repository>> ->
                when (resource) {
                    is Resource.Idle -> Timber.d("Searching repository is idle")
                    is Resource.Loading -> binding.pbSearch.isVisible = true
                    is Resource.Success -> {
                        binding.pbSearch.isVisible = false

                        val result = resource.data as List<Repository>
                        setupAdapter(result)
                    }
                    is Resource.Failure -> {
                        binding.pbSearch.isVisible = false
                        Timber.e(resource.message)
                        snackBar("Error: ${resource.message ?: "Unknown error occurred"}")
                    }
                }
            }
    }

    private fun setupAdapter(result: List<Repository>) {
        searchAdapter.submitList(result)
        binding.rvSearchList.adapter = searchAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val search = menu.findItem(R.id.search_menu)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchForRepositories(query)
            hideKeyboard()
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean = true

    private fun searchForRepositories(query: String) {
        // Make sure we cancel the previous job before creating new one
        searchJob?.cancel()

        searchJob = lifecycleScope.launch {
            viewModel.searchRepositories(mapOf("q" to query, "per_page" to "10"))
        }
    }
}