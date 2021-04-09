/*
 * Created by Karic Kenan on 9.4.2021
 * Copyright (c) 2021 . All rights reserved.
 */

package io.aethibo.fork.framework.utils.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import okio.IOException
import timber.log.Timber

class TimberLoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val t1 = System.nanoTime()

        println()
        Timber.i(
            "Sending request %s on %s%n%s",
            request.url,
            chain.connection(),
            request.headers
        )
        Timber.v("REQUEST BODY BEGINn%snREQUEST BODY END", bodyToString(request))
        val response: Response = chain.proceed(request)
        val responseBody = response.body
        val responseBodyString = response.body?.string()

        /*
        now we have extracted the response body but in the process
        we have consumed the original response and can't read it again
        so we need to build a new one to return from this method
        */
        val newResponse = response.newBuilder().body(
            responseBodyString?.toByteArray()?.toResponseBody(responseBody!!.contentType())
        ).build()
        val t2 = System.nanoTime()
        Timber.i(
            "Received response for %s in %.1fms%n%s",
            response.request.url,
            (t2 - t1) / 1e6,
            response.headers
        )
        Timber.v("RESPONSE BODY BEGIN:n%snRESPONSE BODY END", responseBodyString)
        return newResponse
    }

    companion object {
        private fun bodyToString(request: Request): String {
            return try {
                val copy: Request = request.newBuilder().build()
                val buffer = Buffer()
                copy.body?.writeTo(buffer)
                buffer.readUtf8()
            } catch (e: IOException) {
                "Did not work"
            }
        }
    }
}