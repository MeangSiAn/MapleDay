package com.mosstis.mapleday.data.user.mapper

import com.mosstis.mapleday.core.network.nexon.model.CharacterDto
import com.mosstis.mapleday.core.network.nexon.model.CharacterListResponse
import com.mosstis.mapleday.domain.user.model.AccountCharacter

/** 넥슨 계정이 여러 개일 수 있어 모든 계정의 캐릭터를 하나로 합친다. */
internal fun CharacterListResponse.toDomain(): List<AccountCharacter> =
    accountList.flatMap { account -> account.characterList.map { it.toDomain() } }

private fun CharacterDto.toDomain() = AccountCharacter(
    ocid = ocid,
    name = characterName,
    world = worldName,
    characterClass = characterClass,
    level = characterLevel,
)
