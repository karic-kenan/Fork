package io.aethibo.usecases

import io.aethibo.data.repositories.MainRepository
import io.aethibo.data.utils.Resource
import io.aethibo.domain.AccessTokenResponse

interface GetAccessTokenUseCase {
    suspend operator fun invoke(
        clientId: String,
        clientSecret: String,
        code: String
    ): Resource<AccessTokenResponse>
}

class GetAccessTokenUseCaseImpl(private val repository: MainRepository) : GetAccessTokenUseCase {

    override suspend fun invoke(
        clientId: String,
        clientSecret: String,
        code: String
    ): Resource<AccessTokenResponse> =
        repository.getAccessToken(clientId, clientSecret, code)
}