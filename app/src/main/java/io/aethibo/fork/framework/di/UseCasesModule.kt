package io.aethibo.fork.framework.di

import io.aethibo.usecases.*
import org.koin.dsl.module

val useCasesModule = module {

    /**
     * Authentication
     */
    single<GetAccessTokenUseCase> { GetAccessTokenUseCaseImpl(get()) }

    single<GetCurrentUserUseCase> { GetCurrentUserUseCaseImpl(get()) }

    single<GetUsersRepositoriesUseCase> { GetUsersRepositoriesUseCaseImpl(get()) }

    /**
     * Get events
     */
    single<GetEventsUseCase> { GetEventsUseCaseImpl(get()) }

    /**
     * Get notifications
     */
    single<GetNotificationsUseCase> { GetNotificationsUseCaseImpl(get()) }

    /**
     * Get single repository
     */
    single<GetRepositoryUseCase> { GetRepositoryUseCaseImpl(get()) }

    /**
     * Get repository events
     */
    single<GetRepositoryEventsUseCase> { GetRepositoryEventsUseCaseImpl(get()) }

    /**
     * Search repositories
     */
    single<GetRepositoriesUseCase> { GetRepositoriesUseCaseImpl(get()) }
}