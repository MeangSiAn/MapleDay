package com.mosstis.mapleday.domain.user.usecase

import com.mosstis.mapleday.domain.user.model.AccountCharacter
import com.mosstis.mapleday.domain.user.repository.AccountRepository
import javax.inject.Inject

/**
 * 이미 연결된 계정의 캐릭터 목록 (S05 재진입). 저장된 키로 다시 불러온다.
 * 연결된 계정이 없으면 null. 넥슨 오류는 `NexonApiError` 그대로 전달된다.
 */
class GetLinkedAccountCharactersUseCase @Inject constructor(
    private val repository: AccountRepository,
) {
    suspend operator fun invoke(): List<AccountCharacter>? =
        repository.fetchLinkedAccountCharacters()?.sortedByDescending { it.level }
}
