/*
 * Created by Karic Kenan on 21.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.usecases

import io.aethibo.data.repositories.MainRepository
import io.aethibo.data.utils.Resource
import io.aethibo.domain.Repository

interface GetRepositoryUseCase {
    suspend operator fun invoke(owner: String, repository: String): Resource<Repository>
}

class GetRepositoryUseCaseImpl(private val mainRepository: MainRepository) : GetRepositoryUseCase {

    override suspend fun invoke(owner: String, repository: String): Resource<Repository> =
        mainRepository.getSingleRepository(owner, repository)
}