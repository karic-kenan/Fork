/*
 * Created by Karic Kenan on 8.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.data.mapper

import io.aethibo.data.utils.EntityMapper
import io.aethibo.domain.Repository
import io.aethibo.domain.response.RepositoryResponse

class RepositoryMapper : EntityMapper<RepositoryResponse, Repository> {

    override fun mapFromEntity(entity: RepositoryResponse): Repository =
        Repository(
            id = entity.id.toString(),
            name = entity.name,
            private = entity.private,
            description = entity.description,
            language = entity.language,
            watchersCount = entity.watchersCount,
            stargazersCount = entity.stargazersCount,
            forksCount = entity.forksCount,
            licence = entity.license.name,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            owner = entity.owner.login,
            avatarUrl = entity.owner.avatarUrl
        )

    override fun mapToEntity(domainModel: Repository): RepositoryResponse =
        RepositoryResponse()

    fun mapFromEntityList(entities: List<RepositoryResponse>): List<Repository> {
        return entities.map { mapFromEntity(it) }
    }
}