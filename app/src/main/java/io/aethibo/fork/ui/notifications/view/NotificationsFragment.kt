package io.aethibo.fork.ui.notifications.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import by.kirich1409.viewbindingdelegate.viewBinding
import io.aethibo.data.utils.Resource
import io.aethibo.domain.response.NotificationResponse
import io.aethibo.fork.R
import io.aethibo.fork.databinding.FragmentNotificationsBinding
import io.aethibo.fork.ui.auth.utils.snackBar
import io.aethibo.fork.ui.notifications.viewmodel.NotificationsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    private val binding: FragmentNotificationsBinding by viewBinding()
    private val viewModel: NotificationsViewModel by viewModel()

    companion object {
        fun newInstance() = NotificationsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getNotifications()

        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.notificationsStatus.asLiveData()
            .observe(viewLifecycleOwner) { resource: Resource<List<NotificationResponse>> ->
                when (resource) {
                    is Resource.Loading -> binding.pbNotifications.isVisible = true
                    is Resource.Success -> {
                        binding.pbNotifications.isVisible = false

                        val result: List<NotificationResponse> =
                            resource.data as List<NotificationResponse>

                        // at the time of development, this application had no scope
                        // for notifications, hence error
                    }
                    is Resource.Failure -> {
                        binding.pbNotifications.isVisible = false
                        snackBar("Error: ${resource.message ?: "Unknown error occurred"}")

                        binding.animationView.isVisible = true
                        binding.tvNotificationError.text = resource.message
                    }
                }
            }
    }
}