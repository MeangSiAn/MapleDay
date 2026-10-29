package com.mosstis.mapleday.domain.user.usecase

import com.mosstis.mapleday.domain.user.model.AccountCharacter
import com.mosstis.mapleday.domain.user.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 저장된 유저 캐릭터 정보를 가져온다
 */
class GetMyCharactersUseCase @Inject constructor(
    private val repository : CharacterRepository
) {
    operator fun invoke() : Flow<List<AccountCharacter>> {
        return repository.getCharacter()
    }
}
