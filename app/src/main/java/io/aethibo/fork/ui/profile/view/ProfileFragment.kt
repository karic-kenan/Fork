package io.aethibo.fork.ui.profile.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.CircleCropTransformation
import com.pandora.bottomnavigator.BottomNavigator
import io.aethibo.data.utils.Resource
import io.aethibo.domain.Repository
import io.aethibo.domain.User
import io.aethibo.fork.R
import io.aethibo.fork.databinding.FragmentProfileBinding
import io.aethibo.fork.framework.domain.RepositoryDetailRequest
import io.aethibo.fork.ui.auth.activity.AuthActivity
import io.aethibo.fork.ui.auth.utils.snackBar
import io.aethibo.fork.ui.detail.view.DetailRepositoryFragment
import io.aethibo.fork.ui.profile.adapter.RepositoriesAdapter
import io.aethibo.fork.ui.profile.viewmodel.ProfileViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModel()
    private val binding: FragmentProfileBinding by viewBinding()
    private val repositoryAdapter: RepositoriesAdapter by lazy { RepositoriesAdapter() }
    private lateinit var navigator: BottomNavigator

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigator = BottomNavigator.provide(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel.getCurrentUser()
        viewModel.getUsersRepositories(mapOf("sort" to "updated"))

        subscribeToObservers()
        setupAdapterClickListeners()
    }

    private fun setupAdapterClickListeners() {
        repositoryAdapter.setOnRepositoryClickListener { repository: Repository ->
            val repositoryInfo = RepositoryDetailRequest(repository.owner, repository.name)
            navigator.addFragment(DetailRepositoryFragment.newInstance(repositoryInfo))
        }
    }

    private fun subscribeToObservers() {
        viewModel.userMetadataStatus.asLiveData()
            .observe(viewLifecycleOwner) { result: Resource<User> ->
                when (result) {
                    is Resource.Loading -> binding.profileProgressBar.isVisible = true
                    is Resource.Success -> {
                        binding.profileProgressBar.isVisible = false
                        setupMetadataUi(result.data as User)
                    }
                    is Resource.Failure -> {
                        binding.profileProgressBar.isVisible = false
                        snackBar(result.message ?: "Unknown error occurred!")
                    }
                }
            }

        viewModel.repositoryStatus.asLiveData()
            .observe(viewLifecycleOwner) { result: Resource<List<Repository>> ->
                when (result) {
                    is Resource.Loading -> binding.profileProgressBar.isVisible = true
                    is Resource.Success -> {
                        binding.profileProgressBar.isVisible = false
                        setupRepositoriesAdapter(result.data as List<Repository>)
                    }
                    is Resource.Failure -> {
                        binding.profileProgressBar.isVisible = false
                        snackBar(result.message ?: "Unknown error occurred")
                    }
                }
            }
    }

    private fun setupRepositoriesAdapter(repositories: List<Repository>) {
        repositoryAdapter.submitList(repositories)
        binding.rvProfileRepositories.adapter = repositoryAdapter
    }

    private fun setupMetadataUi(user: User) {
        binding.profileHeader.apply {
            ivProfileAvatar.load(user.avatarUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }

            tvProfileName.text = user.name
            tvProfileUsername.text = getString(R.string.labelUsername, user.login)
            tvProfileBio.text = user.bio
            tvProfileLocation.text = user.location
            tvProfileReposCount.text = user.publicRepos.toString()
            tvProfileFollowersCount.text = user.followers.toString()
            tvProfileFollowingCount.text = user.following.toString()
        }
    }

    private fun signOut() {
        /**
         * Clear out shared preferences containing auth token
         */
        requireContext()
            .getSharedPreferences("XEncryptedSharedPrefs", 0)
            .edit()
            .clear()
            .apply()
            .also {
                /**
                 * Once done we navigate back to auth activity
                 */
                val intent = Intent(requireActivity(), AuthActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) =
        inflater.inflate(R.menu.profile_menu, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sign_out -> signOut()
        }

        return super.onOptionsItemSelected(item)
    }
}