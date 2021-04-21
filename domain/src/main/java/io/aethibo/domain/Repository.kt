/*
 * Created by Karic Kenan on 8.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.domain

data class Repository(
        val id: String = "",
        val name: String = "",
        val private: Boolean = false,
        val description: String = "",
        val language: String = "",
        val watchersCount: Int = 0,
        val stargazersCount: Int = 0,
        val forksCount: Int = 0,
        val licence: String = "",
        val createdAt: String = "",
        val updatedAt: String = "",
        val owner: String = "",
        val avatarUrl: String = ""
)
