package io.aethibo.fork.framework.di

import io.aethibo.usecases.GetCurrentUserUseCase
import io.aethibo.usecases.GetCurrentUserUseCaseImpl
import org.koin.dsl.module

val useCasesModule = module {

    single<GetCurrentUserUseCase> { GetCurrentUserUseCaseImpl(get()) }
}