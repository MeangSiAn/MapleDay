package com.mosstis.mapleday.data.user.di

import com.mosstis.mapleday.data.user.repository.AccountRepositoryImpl
import com.mosstis.mapleday.data.user.repository.CharacterRepositoryImpl
import com.mosstis.mapleday.data.user.repository.UserKeyRepositoryImpl
import com.mosstis.mapleday.domain.user.repository.AccountRepository
import com.mosstis.mapleday.domain.user.repository.CharacterRepository
import com.mosstis.mapleday.domain.user.repository.UserKeyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UserDataModule {

    @Binds
    @Singleton
    abstract fun bindUserKeyRepository(impl: UserKeyRepositoryImpl): UserKeyRepository

    @Binds
    @Singleton
    abstract fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    abstract fun bindCharacterRepository(impl: CharacterRepositoryImpl): CharacterRepository
}
