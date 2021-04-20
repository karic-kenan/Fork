package io.aethibo.data.remote.api

import io.aethibo.domain.response.RepositoryEventsResponse
import io.aethibo.domain.response.RepositoryResponse
import io.aethibo.domain.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {

    /**
     * Current user
     * URL: https://docs.github.com/en/rest/reference/users#get-the-authenticated-user
     */
    @GET("user")
    suspend fun getUserInfo(): UserResponse

    /**
     * Current user repositories
     * URL: https://docs.github.com/en/rest/reference/repos#list-repositories-for-the-authenticated-user
     */
    @GET("user/repos")
    suspend fun getCurrentUserRepos(@QueryMap params: Map<String, String>): List<RepositoryResponse>

    /**
     * Single repository
     * URL: https://docs.github.com/en/rest/reference/repos#get-a-repository
     */
    @GET("repos/{owner}/{repository}")
    suspend fun getSingleRepository(
        @Path("owner") owner: String,
        @Path("repository") repository: String
    ): RepositoryResponse

    /**
     * Repository events
     * URL: https://docs.github.com/en/rest/reference/activity#list-repository-events
     */
    @GET("repos/{owner}/{repository}/events")
    suspend fun getRepositoryEvents(
        @Path("owner") owner: String,
        @Path("repository") repository: String,
        @QueryMap params: Map<String, String>
    ): List<RepositoryEventsResponse>
}