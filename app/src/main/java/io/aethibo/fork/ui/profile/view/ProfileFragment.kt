package io.aethibo.fork.ui.profile.view

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import io.aethibo.data.utils.Resource
import io.aethibo.data.utils.tokenize
import io.aethibo.domain.User
import io.aethibo.fork.R
import io.aethibo.fork.framework.utils.AppConst
import io.aethibo.fork.ui.auth.utils.snackBar
import io.aethibo.fork.ui.profile.viewmodel.ProfileViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val sharedPreferences: SharedPreferences by inject()
    private val viewModel: ProfileViewModel by viewModel()

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = getToken()
        Timber.d("Token: $token")
        viewModel.getCurrentUser(token)

        subscribeToObservers()
    }

    private fun getToken(): String {
        val tokenType = sharedPreferences.getString(AppConst.authTokenType, "")!!
        val token = sharedPreferences.getString(AppConst.authAccessToken, "")!!
        return token.tokenize(tokenType)
    }

    private fun subscribeToObservers() {
        viewModel.userMetadataStatus.asLiveData().observe(viewLifecycleOwner) { result: Resource<User> ->
            when (result) {
                is Resource.Loading -> {
                    Timber.d("Loading user data")
                }
                is Resource.Success -> {
                    val data: User = result.data as User
                    Timber.d("User: ${data.name}")
                }
                is Resource.Failure -> {
                    snackBar(result.message ?: "Unknown error occurred!")
                }
            }
        }
    }
}