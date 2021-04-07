package io.aethibo.data.repositories

import io.aethibo.data.datasource.MainRemoteDataSource
import io.aethibo.data.utils.Resource
import io.aethibo.domain.AccessTokenResponse
import io.aethibo.domain.User

class DefaultMainRepository(private val remote: MainRemoteDataSource) : MainRepository {

    override suspend fun getAccessToken(params: Map<String, String>): Resource<AccessTokenResponse> =
            remote.getAccessToken(params)

    override suspend fun getUserInfo(token: String): Resource<User> =
            remote.getUserInfo(token)
}