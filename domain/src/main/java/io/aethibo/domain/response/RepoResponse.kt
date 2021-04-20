/*
 * Created by Karic Kenan on 20.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.domain.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoResponse(
    val id: Int = 0,
    val name: String = "",
    val url: String = ""
)