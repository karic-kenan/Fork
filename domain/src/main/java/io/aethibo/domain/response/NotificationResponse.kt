/*
 * Created by Karic Kenan on 20.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.domain.response

data class NotificationResponse(
    val id: Int = 0,
    val repository: RepositoryResponse = RepositoryResponse(),
    val subject: SubjectResponse = SubjectResponse(),
    val reason: String = "",
    val unread: String = "",
    val updated_at: String = "",
    val last_read_at: String = "",
    val url: String = ""

)
