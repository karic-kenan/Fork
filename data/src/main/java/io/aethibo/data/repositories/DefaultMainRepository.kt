package io.aethibo.data.repositories

import io.aethibo.data.datasource.MainRemoteDataSource
import io.aethibo.data.utils.Resource
import io.aethibo.domain.Repository
import io.aethibo.domain.User
import io.aethibo.domain.response.AccessTokenResponse

class DefaultMainRepository(private val remote: MainRemoteDataSource) : MainRepository {

    override suspend fun getAccessToken(params: Map<String, String>): Resource<AccessTokenResponse> =
        remote.getAccessToken(params)

    override suspend fun getUserInfo(token: String): Resource<User> =
        remote.getUserInfo(token)

    override suspend fun getCurrentUserRepos(
        token: String,
        params: Map<String, String>
    ): Resource<List<Repository>> =
        remote.getCurrentUserRepos(token, params)
}