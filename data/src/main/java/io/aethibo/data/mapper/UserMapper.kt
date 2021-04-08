package io.aethibo.data.mapper

import io.aethibo.data.utils.EntityMapper
import io.aethibo.domain.User
import io.aethibo.domain.response.UserResponse

class UserMapper : EntityMapper<UserResponse, User> {

    override fun mapFromEntity(entity: UserResponse): User =
            User(name = entity.name,
                    login = entity.login,
                    avatarUrl = entity.avatarUrl,
                    location = entity.location,
                    bio = entity.bio,
                    publicRepos = entity.publicRepos,
                    followers = entity.followers,
                    following = entity.following)

    override fun mapToEntity(domainModel: User): UserResponse = UserResponse(
            name = domainModel.name,
            company = "",
            twitterUsername = ""
    )

    fun mapFromEntityList(entities: List<UserResponse>): List<User> {
        return entities.map { mapFromEntity(it) }
    }
}