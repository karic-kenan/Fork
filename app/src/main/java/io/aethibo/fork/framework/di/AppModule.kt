package io.aethibo.fork.framework.di

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import io.aethibo.data.mapper.RepositoryMapper
import io.aethibo.data.mapper.UserMapper
import io.aethibo.fork.ForkApp
import org.koin.dsl.module

val appModule = module {
    single {
        val mainKey: MasterKey = MasterKey.Builder(ForkApp.instance.applicationContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences(
            ForkApp.instance.applicationContext, // context
            "XEncryptedSharedPrefs", // fileName
            mainKey, // masterKeyAlias
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, //prefKeyEncryptionScheme
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM // prefvalueEncryptionScheme
        )
    }

    /**
     * Mappers
     */
    single { UserMapper() }
    single { RepositoryMapper() }
}