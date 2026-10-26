package com.mosstis.mapleday.domain.user.repository

import com.mosstis.mapleday.domain.user.model.AccountCharacter
import kotlinx.coroutines.flow.Flow

/** 기록하기로 고른 내 캐릭터 (로컬 저장). */
interface CharacterRepository {

    /** 저장된 캐릭터가 한 명이라도 있는지. */
    fun observeHasCharacters(): Flow<Boolean>

    /** 고른 캐릭터를 저장한다. 같은 ocid는 덮어쓴다. */
    suspend fun saveCharacters(characters: List<AccountCharacter>)
}
