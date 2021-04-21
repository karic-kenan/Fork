/*
 * Created by Karic Kenan on 21.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.usecases

import io.aethibo.data.repositories.MainRepository
import io.aethibo.data.utils.Resource
import io.aethibo.domain.Repository

interface GetRepositoriesUseCase {
    suspend operator fun invoke(params: Map<String, String>): Resource<List<Repository>>
}

class GetRepositoriesUseCaseImpl(private val repository: MainRepository) : GetRepositoriesUseCase {

    override suspend fun invoke(params: Map<String, String>): Resource<List<Repository>> =
        repository.searchRepositories(params)
}