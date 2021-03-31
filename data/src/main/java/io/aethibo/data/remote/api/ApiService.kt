package io.aethibo.data.remote.api

import io.aethibo.domain.User

interface ApiService {

    // Current user
    suspend fun getUserInfo(): User
}