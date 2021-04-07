package io.aethibo.fork.framework.datasource

import io.aethibo.data.datasource.MainRemoteDataSource
import io.aethibo.data.mapper.UserMapper
import io.aethibo.data.remote.api.ApiService
import io.aethibo.data.remote.api.AuthService
import io.aethibo.data.utils.Resource
import io.aethibo.data.utils.safeCall
import io.aethibo.domain.User
import io.aethibo.domain.response.AccessTokenResponse
import io.aethibo.domain.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRemoteDataSourceImpl(
        private val service: ApiService,
        private val authService: AuthService,
        private val userMapper: UserMapper
) : MainRemoteDataSource {

    override suspend fun getAccessToken(params: Map<String, String>): Resource<AccessTokenResponse> = withContext(Dispatchers.IO) {
        safeCall {
            val result: AccessTokenResponse = authService.getAccessToken(params)

            Resource.Success(result)
        }
    }

    override suspend fun getUserInfo(token: String): Resource<User> = withContext(Dispatchers.IO) {
        safeCall {

            val response: UserResponse = service.getUserInfo(token)
            val result = userMapper.mapFromEntity(response)

            Resource.Success(result)
        }
    }
}