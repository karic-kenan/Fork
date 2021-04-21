/*
 * Created by Karic Kenan on 21.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.usecases

import io.aethibo.data.repositories.MainRepository
import io.aethibo.data.utils.Resource
import io.aethibo.domain.response.RepositoryEventsResponse

interface GetRepositoryEventsUseCase {
    suspend operator fun invoke(
        owner: String,
        repository: String,
        params: Map<String, String>
    ): Resource<List<RepositoryEventsResponse>>
}

class GetRepositoryEventsUseCaseImpl(private val mainRepository: MainRepository) :
    GetRepositoryEventsUseCase {

    override suspend fun invoke(
        owner: String,
        repository: String,
        params: Map<String, String>
    ): Resource<List<RepositoryEventsResponse>> =
        mainRepository.getRepositoryEvents(owner, repository, params)
}