package com.mosstis.mapleday.domain.character.repository

import com.mosstis.mapleday.domain.character.model.CharacterBasic
import com.mosstis.mapleday.domain.character.model.CharacterScheduler
import com.mosstis.mapleday.domain.character.model.EventNotice
import com.mosstis.mapleday.domain.character.model.Inspection
import kotlinx.coroutines.flow.Flow

/**
 * 캐릭터 상세 상태 (H01 today, G01 성장).
 * 화면은 저장본을 구독하고, 새로고침은 네트워크 → 저장만 한다 (SPEC 6장).
 */
interface CharacterDetailRepository {

    /** 저장된 기본 상태. 아직 받은 적 없으면 null. */
    fun observeBasic(ocid: String): Flow<CharacterBasic?>

    /** 저장된 스케줄러. 아직 받은 적 없으면 null. */
    fun observeScheduler(ocid: String): Flow<CharacterScheduler?>

    /** 진행 중 이벤트. */
    fun observeEvents(): Flow<List<EventNotice>>

    /** 예정된 점검. 없으면 null. */
    fun observeInspection(): Flow<Inspection?>

    /**
     * NAS /app/today로 받아 저장한다. 실패한 블록은 이전 저장본을 그대로 둔다.
     * 요청 자체가 실패하면 `NexonApiError`를 던진다.
     */
    suspend fun refreshToday(ocids: List<String>)
}
