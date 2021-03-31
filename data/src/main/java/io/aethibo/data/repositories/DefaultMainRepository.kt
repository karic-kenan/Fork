package io.aethibo.data.repositories

import io.aethibo.data.datasource.MainRemoteDataSource
import io.aethibo.data.utils.Resource
import io.aethibo.domain.User

class DefaultMainRepository(private val remote: MainRemoteDataSource) : MainRepository {

    override suspend fun getUserInfo(): Resource<User> =
        remote.getUserInfo()
}