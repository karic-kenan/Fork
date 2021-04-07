package io.aethibo.fork.ui.auth.view

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import by.kirich1409.viewbindingdelegate.viewBinding
import io.aethibo.data.utils.Resource
import io.aethibo.domain.AccessTokenResponse
import io.aethibo.fork.ui.MainActivity
import io.aethibo.fork.R
import io.aethibo.fork.databinding.FragmentAuthBinding
import io.aethibo.fork.framework.utils.AppConst
import io.aethibo.fork.ui.auth.utils.openNewTabWindow
import io.aethibo.fork.ui.auth.utils.snackBar
import io.aethibo.fork.ui.auth.viewmodel.AuthViewModel
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class AuthFragment : Fragment(R.layout.fragment_auth), View.OnClickListener {

    private val binding: FragmentAuthBinding by viewBinding()
    private val viewModel: AuthViewModel by viewModel()
    private val sharedPreferences: SharedPreferences by inject()

    companion object {
        fun newInstance() = AuthFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtonClickListeners()
        subscribeToObservers()
    }

    private fun setupButtonClickListeners() {
        binding.btnAuthorize.setOnClickListener(this)
    }

    private fun subscribeToObservers() {
        viewModel.accessTokenStatus.asLiveData().observe(viewLifecycleOwner, ::handleStatesForAccessToken)
    }

    override fun onResume() {
        super.onResume()
        startAccessTokenFetch()
    }

    private fun startAccessTokenFetch() {
        val uri: Uri? = requireActivity().intent.data

        if (uri != null && uri.toString().startsWith(AppConst.redirectUrl)) {
            val code = uri.getQueryParameter(getString(R.string.urlQueryParamCode)) ?: ""

            viewModel.getAccessToken(AppConst.clientId, AppConst.clientSecret, code)
        }
    }

    private fun handleStatesForAccessToken(value: Resource<AccessTokenResponse>) = when (value) {
        is Resource.Idle -> Timber.d("Initializing authentication via GitHub")
        is Resource.Loading -> binding.pbAuthFragment.isVisible = true
        is Resource.Success -> {
            binding.pbAuthFragment.isVisible = false

            val data: AccessTokenResponse = value.data as AccessTokenResponse
            saveAccessTokenAndNavigate(data)
        }
        is Resource.Failure -> {
            binding.pbAuthFragment.isVisible = false

            Timber.e("Error: ${value.message}")
            snackBar("Error: ${value.message ?: "Unknown error occurred"}")
        }
        else -> throw IllegalStateException("Unknown state")
    }

    private fun saveAccessTokenAndNavigate(data: AccessTokenResponse) {

        // Save token to encrypted shared preferences
        // then navigate to MainActivity
        with(sharedPreferences.edit()) {
            apply {
                putString(AppConst.authTokenType, data.tokenType)
                putString(AppConst.authAccessToken, data.accessToken)
                apply()
            }
        }.also {
            val intent = Intent(requireContext(), MainActivity::class.java)
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnAuthorize -> openNewTabWindow(
                    requireContext(),
                    getString(
                            R.string.urlOAuth,
                            AppConst.authorizeUrl,
                            AppConst.clientId,
                            AppConst.redirectUrl
                    )
            )
        }
    }
}