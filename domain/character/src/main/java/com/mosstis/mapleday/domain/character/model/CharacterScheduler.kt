package com.mosstis.mapleday.domain.character.model

import java.time.LocalDate

/** 캐릭터 스케줄러 (H01 오늘 숙제·주간 보스, P01). NAS /app/today 응답의 `scheduler`. */
data class CharacterScheduler(
    val ocid: String,
    /** 기준일 (KST) */
    val date: LocalDate,
    val dailyContents: List<SchedulerContent>,
    val weeklyContents: List<SchedulerContent>,
    val bossContents: List<BossContent>,
    val weeklyBossClearCount: Int,
    val weeklyBossClearLimit: Int,
    /** 받은 시각 (epoch millis) */
    val fetchedAt: Long,
)

/** 일일·주간 콘텐츠 한 줄. */
data class SchedulerContent(
    val name: String,
    /** 게임 안 스케줄러에 등록했는지. 등록한 것만 화면에 보인다. */
    val isRegistered: Boolean,
    val nowCount: Int,
    val maxCount: Int,
    val isQuestCompleted: Boolean,
) {
    /** SPEC H01: `now_count >= max_count` 또는 `quest_state` 완료 */
    val isCompleted: Boolean
        get() = isQuestCompleted || (maxCount > 0 && nowCount >= maxCount)
}

/** 보스 한 줄. */
data class BossContent(
    val name: String,
    val difficulty: String,
    val isRegistered: Boolean,
    val isCleared: Boolean,
    val cycle: BossCycle,
)

/** 월간 보스는 주간 보스 카드 아래 "이번 달"로 따로 보여준다 (SPEC P01). */
enum class BossCycle { WEEKLY, MONTHLY }
