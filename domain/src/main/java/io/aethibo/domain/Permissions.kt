package io.aethibo.domain


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Permissions(
    val admin: Boolean = false,
    val pull: Boolean = false,
    val push: Boolean = false
)