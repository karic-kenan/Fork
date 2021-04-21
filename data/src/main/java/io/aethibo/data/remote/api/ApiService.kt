package io.aethibo.data.remote.api

import io.aethibo.domain.response.*
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
    @GET("repos/{username}/{repo}")
    suspend fun getSingleRepository(
        @Path("username") owner: String,
        @Path("repo") repository: String
    ): RepositoryResponse

    /**
     * Repository events
     * URL: https://docs.github.com/en/rest/reference/activity#list-repository-events
     */
    @GET("repos/{username}/{repo}/events")
    suspend fun getRepositoryEvents(
        @Path("username") owner: String,
        @Path("repo") repository: String,
        @QueryMap params: Map<String, String>
    ): List<RepositoryEventsResponse>

    /**
     * Notifications
     * URL: https://docs.github.com/en/rest/reference/activity#list-notifications-for-the-authenticated-user
     */
    @GET("notifications?per_page=10")
    suspend fun getNotifications(): List<NotificationResponse>

    /**
     * Events
     * URL: https://docs.github.com/en/rest/reference/activity#list-events-received-by-the-authenticated-user
     */
    @GET("users/{username}/received_events?per_page=10")
    suspend fun getEvents(@Path("username") username: String): List<EventsResponse>

    /**
     * Search repositories
     * URL: https://docs.github.com/en/rest/reference/search#search-repositories
     */
    @GET("search/repositories")
    suspend fun searchRepositories(@QueryMap params: Map<String, String>): List<RepositoryResponse>
}