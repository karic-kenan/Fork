package io.aethibo.fork.framework.datasource

import io.aethibo.data.datasource.MainRemoteDataSource
import io.aethibo.data.mapper.RepositoryMapper
import io.aethibo.data.mapper.UserMapper
import io.aethibo.data.remote.api.ApiService
import io.aethibo.data.remote.api.AuthService
import io.aethibo.data.utils.Resource
import io.aethibo.data.utils.safeCall
import io.aethibo.domain.Repository
import io.aethibo.domain.User
import io.aethibo.domain.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRemoteDataSourceImpl(
    private val service: ApiService,
    private val authService: AuthService,
    private val userMapper: UserMapper,
    private val repositoryMapper: RepositoryMapper
) : MainRemoteDataSource {

    override suspend fun getAccessToken(params: Map<String, String>): Resource<AccessTokenResponse> =
        withContext(Dispatchers.IO) {
            safeCall {
                val result: AccessTokenResponse = authService.getAccessToken(params)

                Resource.Success(result)
            }
        }

    override suspend fun getUserInfo(): Resource<User> = withContext(Dispatchers.IO) {
        safeCall {

            val response: UserResponse = service.getUserInfo()
            val result = userMapper.mapFromEntity(response)

            Resource.Success(result)
        }
    }

    override suspend fun getCurrentUserRepos(params: Map<String, String>): Resource<List<Repository>> =
        withContext(Dispatchers.IO) {
            safeCall {
                val response: List<RepositoryResponse> = service.getCurrentUserRepos(params)
                val result = repositoryMapper.mapFromEntityList(response)

                Resource.Success(result)
            }
        }

    override suspend fun getEvents(username: String): Resource<List<EventsResponse>> =
        withContext(Dispatchers.IO) {
            safeCall {
                val response: List<EventsResponse> = service.getEvents(username)

                Resource.Success(response)
            }
        }

    override suspend fun getNotifications(): Resource<List<NotificationResponse>> =
        withContext(Dispatchers.IO) {
            safeCall {
                val response: List<NotificationResponse> = service.getNotifications()

                Resource.Success(response)
            }
        }

    override suspend fun getSingleRepository(
        owner: String,
        repository: String
    ): Resource<Repository> = withContext(Dispatchers.IO) {
        safeCall {
            val response: RepositoryResponse = service.getSingleRepository(owner, repository)
            val result: Repository = repositoryMapper.mapFromEntity(response)

            Resource.Success(result)
        }
    }

    override suspend fun getRepositoryEvents(
        owner: String,
        repository: String,
        params: Map<String, String>
    ): Resource<List<RepositoryEventsResponse>> = withContext(Dispatchers.IO) {
        safeCall {
            val response: List<RepositoryEventsResponse> =
                service.getRepositoryEvents(owner, repository, params)

            Resource.Success(response)
        }
    }

    override suspend fun searchRepositories(params: Map<String, String>): Resource<List<Repository>> =
        withContext(Dispatchers.IO) {
            safeCall {
                val response: List<RepositoryResponse> = service.searchRepositories(params).items
                val result: List<Repository> = repositoryMapper.mapFromEntityList(response)

                Resource.Success(result)
            }
        }
}