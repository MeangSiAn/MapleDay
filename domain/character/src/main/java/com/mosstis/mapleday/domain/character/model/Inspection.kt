package com.mosstis.mapleday.domain.character.model

/** 점검 (H01 "곧 끝나요", P02 점검 빨강 선). NAS /app/today 응답의 `inspection`. */
data class Inspection(
    val title: String,
    /** 넥슨 서비스 코드 (응답 그대로) */
    val serviceCode: Int,
    /** 점검 시작·종료 시각 (epoch millis) */
    val startAt: Long,
    val endAt: Long,
)
