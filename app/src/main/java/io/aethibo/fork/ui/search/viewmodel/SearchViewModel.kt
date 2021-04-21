/*
 * Created by Karic Kenan on 21.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.fork.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.aethibo.data.utils.Resource
import io.aethibo.domain.Repository
import io.aethibo.usecases.GetRepositoriesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val getRepositories: GetRepositoriesUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _repositoriesStatus: MutableStateFlow<Resource<List<Repository>>> =
        MutableStateFlow(Resource.Idle())
    val repositoriesStatus: StateFlow<Resource<List<Repository>>>
        get() = _repositoriesStatus

    fun searchRepositories(params: Map<String, String>) {
        viewModelScope.launch(dispatcher) {
            val result: Resource<List<Repository>> = getRepositories.invoke(params)

            _repositoriesStatus.value = result
        }
    }
}