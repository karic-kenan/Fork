/*
 * Created by Karic Kenan on 8.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.domain.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PermissionsResponse(
    val admin: Boolean = false,
    val pull: Boolean = false,
    val push: Boolean = false
)