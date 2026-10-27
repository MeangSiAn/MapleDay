package com.mosstis.mapleday.data.character.di

import com.mosstis.mapleday.data.character.repository.CharacterDetailRepositoryImpl
import com.mosstis.mapleday.domain.character.repository.CharacterDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CharacterDataModule {

    @Binds
    @Singleton
    abstract fun bindCharacterDetailRepository(impl: CharacterDetailRepositoryImpl): CharacterDetailRepository
}
