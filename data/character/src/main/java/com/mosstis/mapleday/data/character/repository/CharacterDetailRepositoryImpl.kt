package com.mosstis.mapleday.data.character.repository

import com.mosstis.mapleday.core.network.error.apiSafeCall
import com.mosstis.mapleday.core.network.error.nexonCall
import com.mosstis.mapleday.core.network.nas.NasTodayApi
import com.mosstis.mapleday.data.character.mapper.toDomain
import com.mosstis.mapleday.domain.character.model.CharacterBasic
import com.mosstis.mapleday.domain.character.model.CharacterScheduler
import com.mosstis.mapleday.domain.character.model.EventNotice
import com.mosstis.mapleday.domain.character.model.Inspection
import com.mosstis.mapleday.domain.character.model.TodayBlock
import com.mosstis.mapleday.domain.character.repository.CharacterDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

/** NAS /app/today 결과를 메모리에 들고 있는 캐릭터 상세 저장소. */
// [NAS]  refreshToday()에서만 호출 → 결과를 캐시에 쓴다
// [캐시] observeXxx()는 캐시만 읽는다 (네트워크 X)
//        지금: 메모리(이 클래스가 들고 있는 값) / 나중: Room(DAO Flow)
@Singleton
class CharacterDetailRepositoryImpl @Inject constructor(
    // [NAS] GET /app/today — refreshToday()에서만 쓴다
    private val nasTodayApi: NasTodayApi,
) : CharacterDetailRepository {

    // 캐싱 처리
    private data class Cache(
        val basics: Map<String, CharacterBasic> = emptyMap(),
        val schedulers: Map<String, CharacterScheduler> = emptyMap(),
        val events: List<EventNotice> = emptyList(),
        val inspection: Inspection? = null
    )
    private val cache = MutableStateFlow(Cache())

    // [캐시 읽기] 지금: 메모리 캐시 구독 / 나중: Room DAO Flow 구독. NAS 호출 안 함
    override fun observeBasic(ocid: String): Flow<CharacterBasic?> {
        return cache.map { it.basics[ocid] }.distinctUntilChanged()
    }

    // [캐시 읽기] 지금: 메모리 캐시 구독 / 나중: Room DAO Flow 구독. NAS 호출 안 함
    override fun observeScheduler(ocid: String): Flow<CharacterScheduler?> {
        return cache.map { it.schedulers[ocid] }.distinctUntilChanged()
    }

    // [캐시 읽기] 지금: 메모리 캐시 구독 / 나중: Room DAO Flow 구독. NAS 호출 안 함
    override fun observeEvents(): Flow<List<EventNotice>> {
        return cache.map { it.events }.distinctUntilChanged()
    }

    // [캐시 읽기] 지금: 메모리 캐시 구독 / 나중: Room DAO Flow 구독. NAS 호출 안 함
    override fun observeInspection(): Flow<Inspection?> {
        return cache.map { it.inspection }.distinctUntilChanged()
    }

    // [NAS → 캐시 쓰기] NAS 호출 → 매퍼(toDomain) → 성공한 블록만 캐시에 저장 / 나중: Room에 upsert
    override suspend fun refreshToday(ocids: List<String>) {
        val result = apiSafeCall {
            nasTodayApi.getToday(ocids.joinToString(","))
        }.toDomain(now = System.currentTimeMillis())

        cache.update { old ->
            val basics = old.basics.toMutableMap()
            val schedulers = old.schedulers.toMutableMap()
            result.characters.forEach { (ocid, data) ->
                data.basicInfo?.let {
                    basics[ocid] = it
                }
                data.scheduler?.let {
                    schedulers[ocid] = it
                }
            }

            old.copy(
                basics = basics,
                schedulers = schedulers,
                events = when(val block = result.events) {
                    is TodayBlock.Ok -> block.data
                    TodayBlock.Failed -> old.events
                },
                inspection = when (val block = result.inspection) {
                    is TodayBlock.Ok -> block.data
                    TodayBlock.Failed -> old.inspection
                },
            )
        }
    }
}
