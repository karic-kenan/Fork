package io.aethibo.data.remote.api

import io.aethibo.domain.response.RepositoryResponse
import io.aethibo.domain.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface ApiService {

    // Current user
    @GET("user")
    suspend fun getUserInfo(@Header("Authorization") token: String): UserResponse

    // Current user repositories
    @GET("user/repos")
    suspend fun getCurrentUserRepos(
        @Header("Authorization") token: String,
        @QueryMap params: Map<String, String>
    ): List<RepositoryResponse>
}