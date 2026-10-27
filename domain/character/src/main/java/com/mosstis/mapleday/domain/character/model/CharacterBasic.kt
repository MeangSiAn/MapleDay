package com.mosstis.mapleday.domain.character.model

/** 캐릭터 기본 상태 (H01 HeroCard). NAS /app/today 응답의 `basic`. */
data class CharacterBasic(
    val ocid: String,
    val name: String,
    val world: String,
    val characterClass: String,
    /** 전직 차수 (예: 6) */
    val classLevel: Int?,
    val level: Int,
    val exp: Long,
    /** 경험치 % (HeroCard는 소수 3자리 표시) */
    val expRate: Double,
    val guild: String?,
    val imageUrl: String?,
    /** 캐릭터 생성 시각 (epoch millis) */
    val createdAt: Long?,
    /** 받은 시각 (epoch millis). 헤더 "{HH:mm:ss} 기준" */
    val fetchedAt: Long,
)
