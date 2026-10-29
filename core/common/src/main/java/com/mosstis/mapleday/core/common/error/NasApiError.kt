package com.mosstis.mapleday.core.common.error

sealed class NasApiError(
    message : String,
    cause : Throwable? = null
) : Exception(message, cause){
    /** 인증 토큰 에러 **/
    class TokenError : NasApiError("APP_TOKEN_INVALID")
    /** 네트워크 에러 **/
    class Network(cause: Throwable) : NasApiError("NETWORK", cause)
    class Unknown(val errorName: String?, val httpStatus: Int) :
        NasApiError("${errorName ?: "UNKNOWN"} ($httpStatus)")
}
