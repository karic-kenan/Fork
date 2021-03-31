package io.aethibo.fork.framework.datasource

import io.aethibo.data.datasource.MainRemoteDataSource
import io.aethibo.data.remote.api.ApiService
import io.aethibo.data.utils.Resource
import io.aethibo.data.utils.safeCall
import io.aethibo.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRemoteDataSourceImpl(private val service: ApiService) : MainRemoteDataSource {

    override suspend fun getUserInfo(): Resource<User> = withContext(Dispatchers.IO) {
        safeCall {

            val result: User = service.getUserInfo()

            Resource.Success(result)
        }
    }
}