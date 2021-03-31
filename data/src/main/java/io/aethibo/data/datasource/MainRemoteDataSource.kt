package io.aethibo.data.datasource

import io.aethibo.data.utils.Resource
import io.aethibo.domain.User

interface MainRemoteDataSource {

    // Current logged in user
    suspend fun getUserInfo(): Resource<User>
}