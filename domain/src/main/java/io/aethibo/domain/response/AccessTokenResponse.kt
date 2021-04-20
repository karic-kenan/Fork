package io.aethibo.domain.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AccessTokenResponse(
        @Json(name = "access_token")
        val accessToken: String = "",
        val scope: String = "",
        @Json(name = "token_type")
        val tokenType: String = ""
)