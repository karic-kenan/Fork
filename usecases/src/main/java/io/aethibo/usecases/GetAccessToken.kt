package io.aethibo.usecases

import io.aethibo.data.repositories.MainRepository
import io.aethibo.data.utils.Resource
import io.aethibo.domain.response.AccessTokenResponse

interface GetAccessTokenUseCase {
    suspend operator fun invoke(params: Map<String, String>): Resource<AccessTokenResponse>
}

class GetAccessTokenUseCaseImpl(private val repository: MainRepository) : GetAccessTokenUseCase {

    override suspend fun invoke(params: Map<String, String>): Resource<AccessTokenResponse> =
            repository.getAccessToken(params)
}