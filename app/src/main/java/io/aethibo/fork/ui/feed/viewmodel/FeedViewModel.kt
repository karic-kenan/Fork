/*
 * Created by Karic Kenan on 21.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.fork.ui.feed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.aethibo.data.utils.Resource
import io.aethibo.domain.User
import io.aethibo.domain.response.EventsResponse
import io.aethibo.usecases.GetCurrentUserUseCase
import io.aethibo.usecases.GetEventsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedViewModel(
    private val getEvents: GetEventsUseCase,
    private val getUser: GetCurrentUserUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _eventsStatus: MutableStateFlow<Resource<List<EventsResponse>>> =
        MutableStateFlow(Resource.Loading())
    val eventsStatus: StateFlow<Resource<List<EventsResponse>>>
        get() = _eventsStatus

    private val _userStatus: MutableStateFlow<Resource<User>> = MutableStateFlow(Resource.Loading())
    val userStatus: StateFlow<Resource<User>>
        get() = _userStatus

    fun getEvents(username: String) {
        viewModelScope.launch(dispatcher) {
            val result: Resource<List<EventsResponse>> = getEvents.invoke(username)

            _eventsStatus.value = result
        }
    }

    fun getUserInformation() {
        viewModelScope.launch(dispatcher) {
            val result: Resource<User> = getUser.invoke()

            _userStatus.value = result
        }
    }
}