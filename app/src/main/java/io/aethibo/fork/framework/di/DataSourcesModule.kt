package io.aethibo.fork.framework.di

import io.aethibo.data.datasource.MainRemoteDataSource
import io.aethibo.fork.framework.datasource.MainRemoteDataSourceImpl
import org.koin.dsl.module

val dataSourcesModule = module {
    single<MainRemoteDataSource> { MainRemoteDataSourceImpl(get()) }
}