package com.mosstis.mapleday.data.user.mapper

import com.mosstis.mapleday.core.database.entity.CharacterEntity
import com.mosstis.mapleday.domain.user.model.AccountCharacter

internal fun AccountCharacter.toEntity(addedAt: Long) = CharacterEntity(
    ocid = ocid,
    name = name,
    world = world,
    characterClass = characterClass,
    level = level,
    addedAt = addedAt,
)

internal fun CharacterEntity.toDomain() = AccountCharacter(
    ocid = ocid,
    name = name,
    world = world,
    characterClass = characterClass,
    level = level,
)