package io.aethibo.fork.ui.home.viewmodel

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

class HomeViewModel(
    private val getUser: GetCurrentUserUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _userStatus: MutableStateFlow<Resource<User>> = MutableStateFlow(Resource.Loading())
    val userStatus: StateFlow<Resource<User>>
        get() = _userStatus

    fun getUser() {
        viewModelScope.launch(dispatcher) {
            val result = getUser.invoke()

            _userStatus.value = result
        }
    }
}