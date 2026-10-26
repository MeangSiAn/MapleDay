package com.mosstis.mapleday.core.database.di

import android.content.Context
import androidx.room.Room
import com.mosstis.mapleday.core.database.MapleDayDatabase
import com.mosstis.mapleday.core.database.dao.AccountDao
import com.mosstis.mapleday.core.database.dao.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): MapleDayDatabase = Room.databaseBuilder(
        context,
        MapleDayDatabase::class.java,
        MapleDayDatabase.NAME,
    ).build()

    @Provides
    fun provideAccountDao(database: MapleDayDatabase): AccountDao = database.accountDao()

    @Provides
    fun provideCharacterDao(database: MapleDayDatabase): CharacterDao = database.characterDao()
}
