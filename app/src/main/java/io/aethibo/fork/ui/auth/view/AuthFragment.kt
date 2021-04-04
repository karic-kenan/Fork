package io.aethibo.fork.ui.auth.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import io.aethibo.data.utils.Resource
import io.aethibo.data.utils.tokenize
import io.aethibo.domain.AccessTokenResponse
import io.aethibo.fork.R
import io.aethibo.fork.databinding.FragmentAuthBinding
import io.aethibo.fork.framework.utils.AppConst
import io.aethibo.fork.ui.auth.utils.openNewTabWindow
import io.aethibo.fork.ui.auth.utils.snackBar
import io.aethibo.fork.ui.auth.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class AuthFragment : Fragment(), View.OnClickListener {

    private val binding: FragmentAuthBinding by viewBinding()
    private val viewModel: AuthViewModel by viewModel()

    companion object {
        fun newInstance() = AuthFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_auth, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtonClickListeners()
        subscribeToObservers()
    }

    private fun setupButtonClickListeners() {
        binding.btnAuthorize.setOnClickListener(this)
    }

    private fun subscribeToObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.accessTokenStatus.collectLatest { value: Resource<AccessTokenResponse> ->
                when (value) {
                    is Resource.Init -> Timber.d("Initializing authentication via GitHub")
                    is Resource.Loading -> binding.pbAuthFragment.isVisible = true
                    is Resource.Success -> {
                        binding.pbAuthFragment.isVisible = false

                        val data: AccessTokenResponse = value.data as AccessTokenResponse
                        /**
                         * TODO: Encrypt token and save it (encrypted shared prefs or realm?)
                         * TODO: Upon success save, navigate to [MainActivity]
                         */
                        Timber.d("Data: ${data.accessToken}, ${data.tokenType}")
                        Timber.d("Token: ${data.accessToken.tokenize(data.tokenType)}")
                    }
                    is Resource.Failure -> {
                        binding.pbAuthFragment.isVisible = false

                        Timber.e("Error: ${value.message}")
                        snackBar("Error: ${value.message ?: "Unknown error occurred"}")
                    }
                    else -> throw IllegalStateException("Unknown state")
                }
            }
        }
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