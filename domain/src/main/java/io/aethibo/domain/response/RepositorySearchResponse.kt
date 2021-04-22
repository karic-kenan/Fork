/*
 * Created by Karic Kenan on 22.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.domain.response

import com.squareup.moshi.Json

data class RepositorySearchResponse(
    @Json(name = "total_count") val total: Int = 0,
    val items: List<RepositoryResponse> = emptyList(),
    val nextPage: Int? = null
)
