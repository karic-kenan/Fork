/*
 * Created by Karic Kenan on 21.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.fork.ui.notifications.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.aethibo.data.utils.Resource
import io.aethibo.domain.response.NotificationResponse
import io.aethibo.usecases.GetNotificationsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationsViewModel(
    private val getNotifications: GetNotificationsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _notificationsStatus: MutableStateFlow<Resource<List<NotificationResponse>>> =
        MutableStateFlow(Resource.Loading())
    val notificationsStatus: StateFlow<Resource<List<NotificationResponse>>>
        get() = _notificationsStatus

    fun getNotifications() {
        viewModelScope.launch(dispatcher) {
            val result: Resource<List<NotificationResponse>> = getNotifications.invoke()

            _notificationsStatus.value = result
        }
    }
}