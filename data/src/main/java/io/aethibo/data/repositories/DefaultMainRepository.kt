package io.aethibo.data.repositories

import io.aethibo.data.datasource.MainRemoteDataSource
import io.aethibo.data.utils.Resource
import io.aethibo.domain.AccessTokenResponse
import io.aethibo.domain.User

class DefaultMainRepository(private val remote: MainRemoteDataSource) : MainRepository {

    override suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): Resource<AccessTokenResponse> =
        remote.getAccessToken(clientId, clientSecret, code)

    override suspend fun getUserInfo(): Resource<User> =
        remote.getUserInfo()
}