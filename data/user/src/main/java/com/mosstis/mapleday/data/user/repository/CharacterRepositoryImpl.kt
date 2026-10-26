package com.mosstis.mapleday.data.user.repository

import com.mosstis.mapleday.core.database.dao.CharacterDao
import com.mosstis.mapleday.data.user.mapper.toEntity
import com.mosstis.mapleday.domain.user.model.AccountCharacter
import com.mosstis.mapleday.domain.user.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao,
) : CharacterRepository {

    override fun observeHasCharacters(): Flow<Boolean> = characterDao.observeExists()

    override suspend fun saveCharacters(characters: List<AccountCharacter>) {
        val now = System.currentTimeMillis()
        characterDao.upsertAll(characters.map { it.toEntity(addedAt = now) })
    }
}
