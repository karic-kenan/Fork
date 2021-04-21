/*
 * Created by Karic Kenan on 21.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.usecases

import io.aethibo.data.repositories.MainRepository
import io.aethibo.data.utils.Resource
import io.aethibo.domain.response.EventsResponse

interface GetEventsUseCase {
    suspend operator fun invoke(username: String): Resource<List<EventsResponse>>
}

class GetEventsUseCaseImpl(private val repository: MainRepository) : GetEventsUseCase {

    override suspend fun invoke(username: String): Resource<List<EventsResponse>> =
        repository.getEvents(username)
}