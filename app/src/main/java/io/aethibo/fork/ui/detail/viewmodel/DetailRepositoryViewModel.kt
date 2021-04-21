/*
 * Created by Karic Kenan on 21.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.fork.ui.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.aethibo.data.utils.Resource
import io.aethibo.domain.Repository
import io.aethibo.domain.response.RepositoryEventsResponse
import io.aethibo.usecases.GetRepositoryEventsUseCase
import io.aethibo.usecases.GetRepositoryUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailRepositoryViewModel(
    private val getRepository: GetRepositoryUseCase,
    private val getRepositoryEvents: GetRepositoryEventsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _repositoryStatus: MutableStateFlow<Resource<Repository>> =
        MutableStateFlow(Resource.Loading())
    val repositoryStatus: StateFlow<Resource<Repository>>
        get() = _repositoryStatus

    private val _repositoryEventsStatus: MutableStateFlow<Resource<List<RepositoryEventsResponse>>> =
        MutableStateFlow(Resource.Loading())
    val repositoryEventsStatus: StateFlow<Resource<List<RepositoryEventsResponse>>>
        get() = _repositoryEventsStatus

    fun getRepository(owner: String, repository: String) {
        viewModelScope.launch(dispatcher) {
            val result: Resource<Repository> = getRepository.invoke(owner, repository)

            _repositoryStatus.value = result
        }
    }

    fun getRepositoryEventsList(owner: String, repository: String, params: Map<String, String>) {
        viewModelScope.launch(dispatcher) {
            val result: Resource<List<RepositoryEventsResponse>> =
                getRepositoryEvents(owner, repository, params)

            _repositoryEventsStatus.value = result
        }
    }
}