package io.aethibo.fork.ui.home.view

import android.os.Bundle
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import by.kirich1409.viewbindingdelegate.viewBinding
import io.aethibo.data.utils.Resource
import io.aethibo.domain.User
import io.aethibo.fork.R
import io.aethibo.fork.databinding.FragmentHomeBinding
import io.aethibo.fork.ui.auth.utils.openNewTabWindow
import io.aethibo.fork.ui.auth.utils.snackBar
import io.aethibo.fork.ui.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home), View.OnClickListener {

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModel()

    private var user: User? = null

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUser()

        setupUi()
        setupClickListeners()
        subscribeToObservers()
    }

    private fun setupClickListeners() {
        binding.btnHomeRepositories.setOnClickListener(this)
        binding.btnHomeOrganizations.setOnClickListener(this)
    }

    private fun setupUi() {
        binding.btnHomeRepositories.text = HtmlCompat.fromHtml(
            getString(R.string.labelBtnRepositories),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        binding.btnHomeOrganizations.text = HtmlCompat.fromHtml(
            getString(R.string.labelBtnOrganizations),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    }

    private fun subscribeToObservers() {
        viewModel.userStatus.asLiveData().observe(viewLifecycleOwner) { resource: Resource<User> ->
            when (resource) {
                is Resource.Loading -> binding.pbHome.isVisible = true
                is Resource.Success -> {
                    binding.pbHome.isVisible = false
                    binding.llHomeButtons.isVisible = true

                    val result = resource.data as User
                    user = result
                }
                is Resource.Failure -> {
                    binding.pbHome.isVisible = false
                    snackBar("Error: ${resource.message ?: "Unknown error occurred"}")
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnHomeRepositories -> openNewTabWindow(requireContext(), user?.reposUrl!!)
            R.id.btnHomeOrganizations -> openNewTabWindow(
                requireContext(),
                user?.organizationsUrl!!
            )
        }
    }
}