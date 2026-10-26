package com.mosstis.mapleday.core.network.nas

import okhttp3.Interceptor
import okhttp3.Response

/** NAS 요청마다 `X-App-Token`을 붙인다 (SPEC 5.1). */
internal class AppTokenInterceptor(
    private val appToken: String,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (appToken.isBlank()) return chain.proceed(request)
        return chain.proceed(
            request.newBuilder()
                .header(HEADER, appToken)
                .build(),
        )
    }

    companion object {
        const val HEADER = "X-App-Token"
    }
}
