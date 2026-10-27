package com.mosstis.mapleday.domain.character.usecase

import com.mosstis.mapleday.domain.character.repository.CharacterDetailRepository
import javax.inject.Inject

/** 추적 중인 캐릭터 전부를 /app/today 한 번으로 갱신한다 (SPEC SC3). 빈 목록이면 호출하지 않는다. */
class RefreshTodayUseCase @Inject constructor(
    private val repository: CharacterDetailRepository,
) {
    suspend operator fun invoke(ocids: List<String>) {
        if (ocids.isEmpty()) return
        repository.refreshToday(ocids.distinct())
    }
}
