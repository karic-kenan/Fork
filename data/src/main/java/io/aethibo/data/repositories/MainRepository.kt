package io.aethibo.data.repositories

import io.aethibo.data.utils.Resource
import io.aethibo.domain.User

interface MainRepository {

    // Current logged in user
    suspend fun getUserInfo(): Resource<User>
}