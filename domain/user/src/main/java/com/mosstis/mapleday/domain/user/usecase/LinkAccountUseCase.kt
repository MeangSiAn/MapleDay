package com.mosstis.mapleday.domain.user.usecase

import com.mosstis.mapleday.domain.user.error.NoAccountCharacterException
import com.mosstis.mapleday.domain.user.model.AccountCharacter
import com.mosstis.mapleday.domain.user.repository.AccountRepository
import javax.inject.Inject

/**
 * 계정 연결 (SPEC SC2): 키로 캐릭터 목록을 불러오고, 성공하면 키를 저장한다.
 * 캐릭터가 0개면 [NoAccountCharacterException]을 던지고 키는 저장하지 않는다.
 * 넥슨 오류는 `NexonApiError` 그대로 전달된다.
 */
class LinkAccountUseCase @Inject constructor(
    private val repository: AccountRepository,
) {
    suspend operator fun invoke(apiKey: String): List<AccountCharacter> {
        val key = apiKey.trim()
        require(key.isNotEmpty()) { "apiKey is blank" }

        val characters = repository.fetchAccountCharacters(key)
        if (characters.isEmpty()) throw NoAccountCharacterException()

        repository.saveApiKey(key)
        return characters.sortedByDescending { it.level }
    }
}
