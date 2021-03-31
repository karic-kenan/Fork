package io.aethibo.fork.framework.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.aethibo.data.remote.api.ApiService
import io.aethibo.data.remote.api.interceptors.SupportInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single {
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(SupportInterceptor())
            .authenticator(SupportInterceptor())
            .build()

        val converterFactory: MoshiConverterFactory = MoshiConverterFactory.create(
            Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        )

        Retrofit.Builder()
            .baseUrl("add_github_base_url")
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}