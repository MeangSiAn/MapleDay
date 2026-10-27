package com.mosstis.mapleday.domain.character.model

/**
 * NAS /app/today 한 번의 결과 (SPEC SC3).
 * 블록마다 성공·실패가 따로라서 일부만 실패할 수 있다 ("정보를 받지 못했어요 · 다시 받기").
 */
data class TodayData(
    /** key = ocid */
    val characters: Map<String, CharacterData>,
    val events: TodayBlock<List<EventNotice>>,
    /** 예정된 점검이 없으면 Ok(null) */
    val inspection: TodayBlock<Inspection?>,
)

/** 블록 하나의 결과. 응답의 `status`가 ok가 아니면 [Failed]. */
sealed interface TodayBlock<out T> {
    data class Ok<T>(val data: T) : TodayBlock<T>
    data object Failed : TodayBlock<Nothing>
}
