/*
 * Created by Karic Kenan on 20.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.domain.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepositoryEventsResponse(
    val actor: ActorResponse = ActorResponse(),
    @Json(name = "created_at")
    val createdAt: String = "",
    val id: String = "",
    val payload: PayloadResponse = PayloadResponse(),
    val `public`: Boolean = false,
    val repo: RepoResponse = RepoResponse(),
    val type: String = ""
)