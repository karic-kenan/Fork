/*
 * Created by Karic Kenan on 8.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.domain.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LicenseResponse(
    val key: String = "",
    val name: String = "",
    @Json(name = "node_id")
    val nodeId: String = "",
    @Json(name = "spdx_id")
    val spdxId: String = "",
    val url: String = ""
)