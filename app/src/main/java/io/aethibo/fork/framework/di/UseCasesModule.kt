package io.aethibo.fork.framework.di

import io.aethibo.usecases.GetAccessTokenUseCase
import io.aethibo.usecases.GetAccessTokenUseCaseImpl
import io.aethibo.usecases.GetCurrentUserUseCase
import io.aethibo.usecases.GetCurrentUserUseCaseImpl
import org.koin.dsl.module

val useCasesModule = module {

    /**
     * Authentication
     */
    single<GetAccessTokenUseCase> { GetAccessTokenUseCaseImpl(get()) }

    single<GetCurrentUserUseCase> { GetCurrentUserUseCaseImpl(get()) }
}