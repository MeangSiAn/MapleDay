package com.mosstis.mapleday.data.user.repository

import com.mosstis.mapleday.core.database.dao.AccountDao
import com.mosstis.mapleday.core.database.entity.AccountEntity
import com.mosstis.mapleday.core.network.error.nexonCall
import com.mosstis.mapleday.core.network.nexon.NexonDirectApi
import com.mosstis.mapleday.core.security.EncryptedData
import com.mosstis.mapleday.core.security.UserKeyStore
import com.mosstis.mapleday.data.user.mapper.toDomain
import com.mosstis.mapleday.domain.user.model.AccountCharacter
import com.mosstis.mapleday.domain.user.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val nexonDirectApi: NexonDirectApi,
    private val userKeyStore: UserKeyStore,
    private val accountDao: AccountDao,
) : AccountRepository {

    override suspend fun fetchAccountCharacters(apiKey: String): List<AccountCharacter> =
        nexonCall { nexonDirectApi.getCharacterList(apiKey) }.toDomain()

    override suspend fun fetchLinkedAccountCharacters(): List<AccountCharacter>? {
        val account = accountDao.get() ?: return null
        val apiKey = withContext(Dispatchers.Default) {
            userKeyStore.decrypt(EncryptedData(cipherText = account.encryptedApiKey, iv = account.iv))
        }
        return fetchAccountCharacters(apiKey)
    }

    override suspend fun saveApiKey(apiKey: String) {
        val encrypted = withContext(Dispatchers.Default) { userKeyStore.encrypt(apiKey) }
        accountDao.upsert(
            AccountEntity(
                encryptedApiKey = encrypted.cipherText,
                iv = encrypted.iv,
                linkedAt = System.currentTimeMillis(),
            ),
        )
    }
}
