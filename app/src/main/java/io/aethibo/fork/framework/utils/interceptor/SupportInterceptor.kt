/*
 * Created by Karic Kenan on 9.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.fork.framework.utils.interceptor

import android.content.SharedPreferences
import io.aethibo.data.utils.tokenize
import io.aethibo.fork.framework.utils.AppConst
import okhttp3.*
import okio.IOException

class SupportInterceptor(private val sharedPreferences: SharedPreferences) : Interceptor,
    Authenticator {

    private fun getToken(): String {
        val tokenType = sharedPreferences.getString(AppConst.authTokenType, "")!!
        val token = sharedPreferences.getString(AppConst.authAccessToken, "")!!
        return token.tokenize(tokenType)
    }

    /**
     * Interceptor class for setting of the headers for every request
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .header("Content-Type", "application/json")
            .header("Accept", "application/vnd.github.v3+json")
            .build()
        return chain.proceed(request)
    }

    /**
     * Authenticator for when the authToken need to be refresh and updated
     * every time we get a 401 error code
     */
    @Throws(IOException::class)
    override fun authenticate(route: Route?, response: Response): Request? {
        var requestAvailable: Request? = null
        try {
            requestAvailable = response.request.newBuilder()
                .addHeader("Authorization", getToken())
                .build()
            return requestAvailable
        } catch (ex: Exception) {
        }
        return requestAvailable
    }
}