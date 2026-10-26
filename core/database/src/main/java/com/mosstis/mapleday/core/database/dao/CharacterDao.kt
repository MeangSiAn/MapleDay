package com.mosstis.mapleday.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mosstis.mapleday.core.database.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    /** 이미 있는 캐릭터(같은 ocid)는 덮어쓴다. */
    @Upsert
    suspend fun upsertAll(characters: List<CharacterEntity>)

    // 저장된 캐릭터 존재 여부 체크
    @Query("SELECT EXISTS(SELECT 1 FROM character)")
    fun observeExists(): Flow<Boolean>
}
