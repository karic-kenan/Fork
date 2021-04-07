package io.aethibo.data.remote.api

import io.aethibo.domain.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    // Current user
    @GET("user")
    suspend fun getUserInfo(@Header("Authorization") token: String): UserResponse
}