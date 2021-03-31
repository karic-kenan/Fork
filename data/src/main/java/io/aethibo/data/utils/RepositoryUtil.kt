package io.aethibo.data.utils

import retrofit2.HttpException

inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {
    return try {
        action()
    } catch (e: HttpException) {
        Resource.Failure(e.localizedMessage)
    } catch (e: Exception) {
        Resource.Failure(e.localizedMessage)
    }
}