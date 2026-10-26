package com.mosstis.mapleday.core.security.di

import com.mosstis.mapleday.core.security.KeystoreUserKeyStore
import com.mosstis.mapleday.core.security.UserKeyStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SecurityModule {

    @Binds
    @Singleton
    abstract fun bindUserKeyStore(impl: KeystoreUserKeyStore): UserKeyStore
}
