package io.aethibo.data.repositories

import io.aethibo.data.utils.Resource
import io.aethibo.domain.Repository
import io.aethibo.domain.User
import io.aethibo.domain.response.AccessTokenResponse

interface MainRepository {

    /**
     * Authorization
     */
    suspend fun getAccessToken(params: Map<String, String>): Resource<AccessTokenResponse>

    // Current logged in user
    suspend fun getUserInfo(token: String): Resource<User>

    // Current user repos
    suspend fun getCurrentUserRepos(token: String, params: Map<String, String>): Resource<List<Repository>>
}