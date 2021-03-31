package io.aethibo.domain


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Plan(
    val collaborators: Int = 0,
    val name: String = "",
    @Json(name = "private_repos")
    val privateRepos: Int = 0,
    val space: Int = 0
)