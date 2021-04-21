package io.aethibo.data.repositories

import io.aethibo.data.utils.Resource
import io.aethibo.domain.Repository
import io.aethibo.domain.User
import io.aethibo.domain.response.AccessTokenResponse
import io.aethibo.domain.response.EventsResponse
import io.aethibo.domain.response.NotificationResponse
import io.aethibo.domain.response.RepositoryEventsResponse

interface MainRepository {

    /**
     * Authorization
     */
    suspend fun getAccessToken(params: Map<String, String>): Resource<AccessTokenResponse>

    // User
    suspend fun getUserInfo(): Resource<User>

    // User repositories
    suspend fun getCurrentUserRepos(params: Map<String, String>): Resource<List<Repository>>

    // Events
    suspend fun getEvents(username: String): Resource<List<EventsResponse>>

    // Notifications
    suspend fun getNotifications(): Resource<List<NotificationResponse>>

    // Single repository
    suspend fun getSingleRepository(owner: String, repository: String): Resource<Repository>

    // Repository events
    suspend fun getRepositoryEvents(
        owner: String,
        repository: String,
        params: Map<String, String>
    ): Resource<List<RepositoryEventsResponse>>

    // Search repositories
    suspend fun searchRepositories(params: Map<String, String>): Resource<List<Repository>>
}