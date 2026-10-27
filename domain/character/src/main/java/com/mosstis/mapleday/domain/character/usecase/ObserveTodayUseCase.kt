package com.mosstis.mapleday.domain.character.usecase

import com.mosstis.mapleday.domain.character.model.EventNotice
import com.mosstis.mapleday.domain.character.model.SchedulerContent
import com.mosstis.mapleday.domain.character.model.TodayOverview
import com.mosstis.mapleday.domain.character.repository.CharacterDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

/** 캐릭터 기본 상태 구독 (H01 HeroCard). 아직 받은 적 없으면 null. */
class ObserveTodayUseCase @Inject constructor(
    private val repository: CharacterDetailRepository,
) {
    operator fun invoke(
        ocid: String,
        now: Long = System.currentTimeMillis()
    ): Flow<TodayOverview> = combine(
        repository.observeBasic(ocid),
        repository.observeScheduler(ocid),
        repository.observeEvents(),
        repository.observeInspection()
    ) { basic, scheduler, events, inspection ->
        TodayOverview(
            basic = basic,
            scheduler = scheduler,
            todayTasks = scheduler?.dailyContents.orEmpty().toTodayTasks(),
            endingSoonEvents = events.endingSoon(now),
            inspection = inspection?.takeIf { it.endAt > now }
        )
    }

    // 등록한 것, 미완료 기준
    private fun List<SchedulerContent>.toTodayTasks() : List<SchedulerContent> = filter {
        it.isRegistered
    }.sortedBy { it.isCompleted }

    // 아직 안 끝났고 7일 안에 끝나는 이벤트, 임박한 순으로 3개 (SPEC H01-5)
    private fun List<EventNotice>.endingSoon(now: Long): List<EventNotice> =
        filter { event -> event.endAt?.let { it in now..(now + SEVEN_DAYS_MILLIS) } == true }
            .sortedBy { it.endAt }
            .take(ENDING_SOON_MAX)

    private companion object {
        const val SEVEN_DAYS_MILLIS = 7L * 24 * 60 * 60 * 1000
        const val ENDING_SOON_MAX = 3
    }
}
