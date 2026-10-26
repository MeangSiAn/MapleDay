package com.mosstis.mapleday.domain.user.model

/** 넥슨 계정에 있는 캐릭터 (S05 목록 한 행). */
data class AccountCharacter(
    val ocid: String,
    val name: String,
    val world: String,
    val characterClass: String,
    val level: Int,
)
