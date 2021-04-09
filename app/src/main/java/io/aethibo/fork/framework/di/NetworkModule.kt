package io.aethibo.fork.framework.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.aethibo.data.remote.api.ApiService
import io.aethibo.data.remote.api.AuthService
import io.aethibo.fork.framework.utils.AppConst
import io.aethibo.fork.framework.utils.interceptor.SupportInterceptor
import io.aethibo.fork.framework.utils.interceptor.TimberLoggingInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single {
        val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(SupportInterceptor(get()))
                .addInterceptor(TimberLoggingInterceptor())
                .authenticator(SupportInterceptor(get()))
                .build()

        val converterFactory: MoshiConverterFactory = MoshiConverterFactory.create(
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        )

        Retrofit.Builder()
                .baseUrl(AppConst.githubApiUrl)
                .addConverterFactory(converterFactory)
                .client(client)
                .build()
                .create(ApiService::class.java)
    }

    single {
        val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(SupportInterceptor(get()))
                .build()

        val converterFactory: MoshiConverterFactory = MoshiConverterFactory.create(
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        )

        Retrofit.Builder()
                .baseUrl(AppConst.githubAuthUrl)
                .addConverterFactory(converterFactory)
                .client(client)
                .build()
                .create(AuthService::class.java)
    }
}