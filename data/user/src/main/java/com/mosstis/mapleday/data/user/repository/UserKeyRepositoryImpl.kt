package com.mosstis.mapleday.data.user.repository

import com.mosstis.mapleday.core.database.dao.AccountDao
import com.mosstis.mapleday.domain.user.repository.UserKeyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserKeyRepositoryImpl @Inject constructor(
    private val accountDao : AccountDao
) : UserKeyRepository {
    override fun getUserKey(): Flow<Boolean> {
        return accountDao.observeExists()
    }
}