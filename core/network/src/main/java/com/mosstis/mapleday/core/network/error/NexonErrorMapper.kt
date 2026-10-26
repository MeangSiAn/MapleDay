package com.mosstis.mapleday.core.network.error

import com.mosstis.mapleday.core.common.error.NexonApiError
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException

/**
 * 넥슨 호출을 감싸 Retrofit/OkHttp 예외를 [NexonApiError]로 바꾼다.
 * 그 밖의 예외(취소 포함)는 그대로 던진다.
 */
suspend fun <T> nexonCall(block: suspend () -> T): T =
    try {
        block()
    } catch (e: HttpException) {
        throw e.toNexonApiError()
    } catch (e: IOException) {
        throw NexonApiError.Network(e)
    }

internal fun HttpException.toNexonApiError(): NexonApiError {
    val name = response()?.errorBody()?.string()?.let(::parseErrorName)
    return mapNexonError(httpStatus = code(), errorName = name)
}

internal fun mapNexonError(httpStatus: Int, errorName: String?): NexonApiError = when (errorName) {
    "OPENAPI00002" -> NexonApiError.Forbidden()
    "OPENAPI00003" -> NexonApiError.NotFound()
    "OPENAPI00005" -> NexonApiError.InvalidApiKey()
    "OPENAPI00007" -> NexonApiError.RateLimited()
    "OPENAPI00009" -> NexonApiError.DataPreparing()
    "OPENAPI00010", "OPENAPI00011" -> NexonApiError.Maintenance(errorName)
    else -> if (httpStatus == 429) NexonApiError.RateLimited() else NexonApiError.Unknown(errorName, httpStatus)
}

/** `{"error":{"name":"OPENAPI00005","message":"..."}}`에서 이름만 꺼낸다. 형식이 다르면 null. */
internal fun parseErrorName(body: String): String? =
    runCatching { errorJson.decodeFromString<NexonErrorBody>(body).error.name }.getOrNull()

private val errorJson = Json { ignoreUnknownKeys = true }

@Serializable
private class NexonErrorBody(val error: Detail) {
    @Serializable
    class Detail(val name: String)
}
