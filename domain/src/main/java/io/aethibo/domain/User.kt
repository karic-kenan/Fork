package io.aethibo.domain

data class User(
        val name: String = "",
        val login: String = "",
        val avatarUrl: String = "",
        val location: String = "",
        val bio: String = "",
        val publicRepos: Int = 0,
        val followers: Int = 0,
        val following: Int = 0
)
