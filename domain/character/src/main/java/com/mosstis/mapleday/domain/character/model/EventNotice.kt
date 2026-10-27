package com.mosstis.mapleday.domain.character.model

/** 진행 중 이벤트 (H01 "곧 끝나요", P02 캘린더). NAS /app/today 응답의 `events`. */
data class EventNotice(
    val id: Long,
    val title: String,
    /** 넥슨 원문 URL (Custom Tabs로 연다) */
    val url: String,
    val thumbnailUrl: String?,
    /** 게시 시각 (epoch millis) */
    val postedAt: Long,
    /** 이벤트 시작·종료 시각 (epoch millis) */
    val startAt: Long?,
    val endAt: Long?,
)
