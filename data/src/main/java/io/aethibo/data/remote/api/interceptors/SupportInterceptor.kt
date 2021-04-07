package io.aethibo.data.remote.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class SupportInterceptor : Interceptor {

    /**
     * Interceptor class for setting of the headers for every request
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .build()
        return chain.proceed(request)
    }

    /**
     * Authenticator for when the authToken need to be refresh and updated
     * every time we get a 401 error code
     */
    /*@Throws(IOException::class)
    override fun authenticate(route: Route?, response: Response): Request? {
        var requestAvailable: Request? = null
        try {
            requestAvailable = response.request.newBuilder()
                .addHeader("Authorization", "add_auth_token_here")
                .build()
            return requestAvailable
        } catch (ex: Exception) {
        }
        return requestAvailable
    }*/
}