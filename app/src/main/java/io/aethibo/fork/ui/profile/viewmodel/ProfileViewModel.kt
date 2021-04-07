package io.aethibo.fork.ui.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.aethibo.data.utils.Resource
import io.aethibo.domain.User
import io.aethibo.usecases.GetCurrentUserUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val getUser: GetCurrentUserUseCase, private val dispatcher: CoroutineDispatcher = Dispatchers.Main) : ViewModel() {

    private val _userMetadataStatus: MutableStateFlow<Resource<User>> =
            MutableStateFlow(Resource.Loading())
    val userMetadataStatus: StateFlow<Resource<User>>
        get() = _userMetadataStatus

    fun getCurrentUser(token: String) {
        viewModelScope.launch(dispatcher) {
            val result: Resource<User> = getUser.invoke(token)

            _userMetadataStatus.value = result
        }
    }
}