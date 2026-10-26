package com.mosstis.mapleday.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.mosstis.mapleday.core.database.dao.AccountDao
import com.mosstis.mapleday.core.database.dao.CharacterDao
import com.mosstis.mapleday.core.database.entity.AccountEntity
import com.mosstis.mapleday.core.database.entity.CharacterEntity

@Database(
    entities = [AccountEntity::class, CharacterEntity::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2), // character 테이블 추가
    ],
)
abstract class MapleDayDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun characterDao(): CharacterDao

    companion object {
        const val NAME = "mapleday.db"
    }
}
