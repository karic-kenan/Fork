package io.aethibo.data.repositories

import io.aethibo.data.utils.Resource
import io.aethibo.domain.Repository
import io.aethibo.domain.User
import io.aethibo.domain.response.AccessTokenResponse
import io.aethibo.domain.response.EventsResponse

interface MainRepository {

    /**
     * Authorization
     */
    suspend fun getAccessToken(params: Map<String, String>): Resource<AccessTokenResponse>

    // Current logged in user
    suspend fun getUserInfo(): Resource<User>

    // Current user repos
    suspend fun getCurrentUserRepos(params: Map<String, String>): Resource<List<Repository>>

    // Current user events
    suspend fun getEvents(username: String): Resource<List<EventsResponse>>
}