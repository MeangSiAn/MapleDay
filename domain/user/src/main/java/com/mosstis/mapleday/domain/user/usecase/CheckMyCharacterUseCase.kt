package com.mosstis.mapleday.domain.user.usecase

import com.mosstis.mapleday.domain.user.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/** 저장된 내 캐릭터가 있는지 (스플래시 분기: 있으면 메인, 없으면 계정 연결). */
class CheckMyCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository,
) {
    operator fun invoke(): Flow<Boolean> = repository.observeHasCharacters()
}
