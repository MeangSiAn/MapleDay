package com.mosstis.mapleday.core.network.nas

import com.mosstis.mapleday.core.network.nas.model.TodayResponse
import retrofit2.http.GET
import retrofit2.http.Query

/** NAS 앱 전용 API (SPEC 5.1). 요청마다 `X-App-Token`이 붙는다 ([AppTokenInterceptor]). */
interface NasTodayApi {

    /** 추적 중인 캐릭터 전부를 한 번에 (SPEC SC3). [ocids]는 쉼표로 이은 문자열. */
    @GET("app/today")
    suspend fun getToday(
        @Query("ocids") ocids: String,
    ): TodayResponse
}
