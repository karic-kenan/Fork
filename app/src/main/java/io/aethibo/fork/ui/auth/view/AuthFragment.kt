package io.aethibo.fork.ui.auth.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.afollestad.vvalidator.form
import com.google.firebase.auth.FirebaseAuth
import io.aethibo.fork.R
import io.aethibo.fork.databinding.FragmentAuthBinding
import timber.log.Timber

class AuthFragment : Fragment() {

    private val binding: FragmentAuthBinding by viewBinding()
    private lateinit var auth: FirebaseAuth

    companion object {
        fun newInstance() = AuthFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_auth, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        validateForm()
    }

    private fun validateForm() {
        form {
            inputLayout(binding.tilAuthUsername, name = "Username") {
                isNotEmpty()
            }
            submitWith(binding.btnAuthorize) { result ->
                // this block is only called if form is valid.
                // do something with a valid form state.
                Timber.d("Success: ${result.success()} - ${binding.etAuthUsername.text?.trim()}")
            }
        }
    }
}