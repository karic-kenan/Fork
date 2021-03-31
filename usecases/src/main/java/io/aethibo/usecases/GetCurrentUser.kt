package io.aethibo.usecases

import io.aethibo.data.repositories.MainRepository
import io.aethibo.data.utils.Resource
import io.aethibo.domain.User

interface GetCurrentUserUseCase {
    suspend operator fun invoke(): Resource<User>
}

class GetCurrentUserUseCaseImpl(private val repository: MainRepository) : GetCurrentUserUseCase {

    override suspend operator fun invoke(): Resource<User> =
        repository.getUserInfo()
}