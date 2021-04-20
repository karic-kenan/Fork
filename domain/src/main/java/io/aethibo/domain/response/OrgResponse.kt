/*
 * Created by Karic Kenan on 20.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.domain.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrgResponse(
    @Json(name = "avatar_url")
    val avatarUrl: String = "",
    @Json(name = "gravatar_id")
    val gravatarId: String = "",
    val id: Int = 0,
    val login: String = "",
    val url: String = ""
)