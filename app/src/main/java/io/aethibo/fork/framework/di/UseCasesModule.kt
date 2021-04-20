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
}