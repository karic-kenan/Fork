package io.aethibo.data.repositories

import io.aethibo.data.datasource.MainRemoteDataSource
import io.aethibo.data.utils.Resource
import io.aethibo.domain.Repository
import io.aethibo.domain.User
import io.aethibo.domain.response.*

class DefaultMainRepository(private val remote: MainRemoteDataSource) : MainRepository {

    override suspend fun getAccessToken(params: Map<String, String>): Resource<AccessTokenResponse> =
        remote.getAccessToken(params)

    override suspend fun getUserInfo(): Resource<User> =
        remote.getUserInfo()

    override suspend fun getCurrentUserRepos(params: Map<String, String>): Resource<List<Repository>> =
        remote.getCurrentUserRepos(params)

    override suspend fun getEvents(username: String): Resource<List<EventsResponse>> =
        remote.getEvents(username)

    override suspend fun getNotifications(): Resource<List<NotificationResponse>> =
        remote.getNotifications()

    override suspend fun getSingleRepository(
        owner: String,
        repository: String
    ): Resource<Repository> = remote.getSingleRepository(owner, repository)

    override suspend fun getRepositoryEvents(
        owner: String,
        repository: String,
        params: Map<String, String>
    ): Resource<List<RepositoryEventsResponse>> =
        remote.getRepositoryEvents(owner, repository, params)
}