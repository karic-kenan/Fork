package io.aethibo.fork.ui.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.aethibo.data.utils.Resource
import io.aethibo.domain.Repository
import io.aethibo.domain.User
import io.aethibo.usecases.GetCurrentUserUseCase
import io.aethibo.usecases.GetUsersRepositoriesUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUser: GetCurrentUserUseCase,
    private val getUsersRepositories: GetUsersRepositoriesUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _userMetadataStatus: MutableStateFlow<Resource<User>> =
        MutableStateFlow(Resource.Loading())
    val userMetadataStatus: StateFlow<Resource<User>>
        get() = _userMetadataStatus

    private val _repositoryStatus: MutableStateFlow<Resource<List<Repository>>> =
        MutableStateFlow(Resource.Loading())
    val repositoryStatus: StateFlow<Resource<List<Repository>>>
        get() = _repositoryStatus

    fun getCurrentUser() {
        viewModelScope.launch(dispatcher) {
            val result: Resource<User> = getUser.invoke()

            _userMetadataStatus.value = result
        }
    }

    fun getUsersRepositories(params: Map<String, String>) {
        viewModelScope.launch(dispatcher) {
            val result: Resource<List<Repository>> = getUsersRepositories.invoke(params)

            _repositoryStatus.value = result
        }
    }
}