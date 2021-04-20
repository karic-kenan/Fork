package io.aethibo.data.remote.api

import io.aethibo.domain.response.AccessTokenResponse
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {

    /**
     * Authorization
     */
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    suspend fun getAccessToken(@FieldMap(encoded = true) params: Map<String, String>): AccessTokenResponse
}