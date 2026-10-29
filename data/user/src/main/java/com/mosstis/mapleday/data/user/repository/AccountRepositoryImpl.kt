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
import java.security.GeneralSecurityException
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
        val apiKey = try {
            withContext(Dispatchers.Default) {
                userKeyStore.decrypt(EncryptedData(cipherText = account.encryptedApiKey, iv = account.iv))
            }
        } catch (e: GeneralSecurityException) {
            // Keystore 키가 사라졌거나 무효화됨(다른 기기로 복원, 잠금 설정 변경 등) → 다시 복호화할 방법이 없다.
            // 깨진 계정을 지우고 "연결 안 됨"으로 돌려 키를 다시 입력받는다.
            accountDao.clear()
            return null
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
