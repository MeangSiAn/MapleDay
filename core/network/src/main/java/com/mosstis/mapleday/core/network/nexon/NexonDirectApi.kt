package com.mosstis.mapleday.core.network.nexon

import com.mosstis.mapleday.core.network.nexon.model.CharacterListResponse
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * 사용자 키로 넥슨에 직접 부르는 계정 전용 API (SPEC D3, 5.2). 사용자 키는 NAS로 보내지 않는다.
 */
interface NexonDirectApi {

    /** 계정의 보유 캐릭터 목록. 연결 전 키 검증도 겸하므로 키를 호출마다 받는다. */
    @GET("maplestory/v1/character/list")
    suspend fun getCharacterList(
        @Header(API_KEY_HEADER) apiKey: String,
    ): CharacterListResponse

    companion object {
        const val BASE_URL = "https://open.api.nexon.com/"
        const val API_KEY_HEADER = "x-nxopen-api-key"
    }
}
