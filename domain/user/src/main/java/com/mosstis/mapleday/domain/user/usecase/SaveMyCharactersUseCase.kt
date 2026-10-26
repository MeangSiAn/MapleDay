package com.mosstis.mapleday.domain.user.usecase

import com.mosstis.mapleday.domain.user.model.AccountCharacter
import com.mosstis.mapleday.domain.user.repository.CharacterRepository
import javax.inject.Inject

/** S05에서 고른 캐릭터를 저장한다. 0명이면 저장하지 않는다 (SPEC: 0명 선택이면 버튼 비활성). */
class SaveMyCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository,
) {
    suspend operator fun invoke(characters: List<AccountCharacter>) {
        require(characters.isNotEmpty()) { "characters is empty" }
        repository.saveCharacters(characters)
    }
}
