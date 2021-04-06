package io.aethibo.fork.framework.datasource

import io.aethibo.data.datasource.MainRemoteDataSource
import io.aethibo.data.remote.api.ApiService
import io.aethibo.data.remote.api.AuthService
import io.aethibo.data.utils.Resource
import io.aethibo.data.utils.safeCall
import io.aethibo.domain.AccessTokenResponse
import io.aethibo.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRemoteDataSourceImpl(
    private val service: ApiService,
    private val authService: AuthService
) : MainRemoteDataSource {

    override suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): Resource<AccessTokenResponse> = withContext(Dispatchers.IO) {
        safeCall {
            val result: AccessTokenResponse =
                authService.getAccessToken(clientId, clientSecret, code)

            Resource.Success(result)
        }
    }

    override suspend fun getUserInfo(): Resource<User> = withContext(Dispatchers.IO) {
        safeCall {

            val result: User = service.getUserInfo()

            Resource.Success(result)
        }
    }
}