package com.mosstis.mapleday.core.network.error

import com.mosstis.mapleday.core.common.error.NasApiError
import okio.IOException
import retrofit2.HttpException

suspend fun <T> apiSafeCall(block : suspend () -> T) : T =
    try {
        block()
    } catch (e : HttpException) {
        throw e.toApiError()
    } catch (e : IOException) {
        throw NasApiError.Network(e)
    }

internal fun HttpException.toApiError() : NasApiError {
    val name = response()?.errorBody()?.string()?.let(::parseErrorName)
    return mapApiError(code(),name)
}

internal fun mapApiError(httpStatus: Int, errorName : String?) : NasApiError = when(errorName) {
    "APP_TOKEN_INVALID" -> NasApiError.TokenError()
    else -> {
        NasApiError.Unknown(errorName, httpStatus)
    }
}
