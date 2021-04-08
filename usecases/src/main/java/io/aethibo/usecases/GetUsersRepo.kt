/*
 * Created by Karic Kenan on 8.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.usecases

import io.aethibo.data.repositories.MainRepository
import io.aethibo.data.utils.Resource
import io.aethibo.domain.Repository

interface GetUsersRepositoriesUseCase {
    suspend operator fun invoke(
        token: String,
        params: Map<String, String>
    ): Resource<List<Repository>>
}

class GetUsersRepositoriesUseCaseImpl(private val repository: MainRepository) :
    GetUsersRepositoriesUseCase {

    override suspend fun invoke(
        token: String,
        params: Map<String, String>
    ): Resource<List<Repository>> =
        repository.getCurrentUserRepos(token, params)
}