/*
 * Created by Karic Kenan on 21.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.usecases

import io.aethibo.data.repositories.MainRepository
import io.aethibo.data.utils.Resource
import io.aethibo.domain.response.NotificationResponse

interface GetNotificationsUseCase {
    suspend operator fun invoke(): Resource<List<NotificationResponse>>
}

class GetNotificationsUseCaseImpl(private val repository: MainRepository) :
    GetNotificationsUseCase {

    override suspend operator fun invoke(): Resource<List<NotificationResponse>> =
        repository.getNotifications()
}