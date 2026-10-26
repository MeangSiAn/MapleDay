package com.mosstis.mapleday.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mosstis.mapleday.core.database.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    /** 다시 연결하면 같은 행(id = ACCOUNT_ID)을 덮어쓴다. */
    @Upsert
    suspend fun upsert(account: AccountEntity)

    // UserKey 존재 여부 체크
    @Query("SELECT EXISTS(SELECT 1 FROM account WHERE id = ${AccountEntity.ACCOUNT_ID})")
    fun observeExists(): Flow<Boolean>

    @Query("SELECT * FROM account WHERE id = ${AccountEntity.ACCOUNT_ID}")
    fun observe(): Flow<AccountEntity?>

    @Query("SELECT * FROM account WHERE id = ${AccountEntity.ACCOUNT_ID}")
    suspend fun get(): AccountEntity?

    /** 계정 연결 해제 (SPEC SC12) */
    @Query("DELETE FROM account")
    suspend fun clear()
}
