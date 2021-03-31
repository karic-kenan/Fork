package io.aethibo.fork.framework.di

import io.aethibo.data.repositories.DefaultMainRepository
import io.aethibo.data.repositories.MainRepository
import org.koin.dsl.module

val repositoriesModule = module {
    single<MainRepository> { DefaultMainRepository(get()) }
}