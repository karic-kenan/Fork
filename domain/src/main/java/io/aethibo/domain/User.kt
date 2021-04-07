package io.aethibo.domain


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "avatar_url")
    val avatarUrl: String = "",
    val bio: String = "",
    val blog: String = "",
    val collaborators: Int = 0,
    val company: String?,
    @Json(name = "created_at")
    val createdAt: String = "",
    @Json(name = "disk_usage")
    val diskUsage: Int = 0,
    val email: String = "",
    @Json(name = "events_url")
    val eventsUrl: String = "",
    val followers: Int = 0,
    @Json(name = "followers_url")
    val followersUrl: String = "",
    val following: Int = 0,
    @Json(name = "following_url")
    val followingUrl: String = "",
    @Json(name = "gists_url")
    val gistsUrl: String = "",
    @Json(name = "gravatar_id")
    val gravatarId: String = "",
    val hireable: Boolean = false,
    @Json(name = "html_url")
    val htmlUrl: String = "",
    val id: Int = 0,
    val location: String = "",
    val login: String = "",
    val name: String = "",
    @Json(name = "node_id")
    val nodeId: String = "",
    @Json(name = "organizations_url")
    val organizationsUrl: String = "",
    @Json(name = "owned_private_repos")
    val ownedPrivateRepos: Int = 0,
    val plan: Plan = Plan(),
    @Json(name = "private_gists")
    val privateGists: Int = 0,
    @Json(name = "public_gists")
    val publicGists: Int = 0,
    @Json(name = "public_repos")
    val publicRepos: Int = 0,
    @Json(name = "received_events_url")
    val receivedEventsUrl: String = "",
    @Json(name = "repos_url")
    val reposUrl: String = "",
    @Json(name = "site_admin")
    val siteAdmin: Boolean = false,
    @Json(name = "starred_url")
    val starredUrl: String = "",
    @Json(name = "subscriptions_url")
    val subscriptionsUrl: String = "",
    @Json(name = "total_private_repos")
    val totalPrivateRepos: Int = 0,
    @Json(name = "twitter_username")
    val twitterUsername: String?,
    @Json(name = "two_factor_authentication")
    val twoFactorAuthentication: Boolean = false,
    val type: String = "",
    @Json(name = "updated_at")
    val updatedAt: String = "",
    val url: String = ""
)