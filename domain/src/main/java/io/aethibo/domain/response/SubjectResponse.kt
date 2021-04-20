/*
 * Created by Karic Kenan on 20.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.domain.response

import com.squareup.moshi.Json

data class SubjectResponse(
    val title: String = "",
    val url: String = "",
    @Json(name = "latest_comment_url")
    val latestCommentUrl: String = "",
    val type: String = ""
)
