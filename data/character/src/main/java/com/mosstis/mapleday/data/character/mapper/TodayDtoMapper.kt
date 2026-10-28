package com.mosstis.mapleday.data.character.mapper

import com.mosstis.mapleday.core.network.nas.model.BasicDto
import com.mosstis.mapleday.core.network.nas.model.BlockDto
import com.mosstis.mapleday.core.network.nas.model.BossContentDto
import com.mosstis.mapleday.core.network.nas.model.EventNoticeDto
import com.mosstis.mapleday.core.network.nas.model.InspectionDto
import com.mosstis.mapleday.core.network.nas.model.SchedulerContentDto
import com.mosstis.mapleday.core.network.nas.model.SchedulerDto
import com.mosstis.mapleday.core.network.nas.model.TodayResponse
import com.mosstis.mapleday.domain.character.model.BossContent
import com.mosstis.mapleday.domain.character.model.BossCycle
import com.mosstis.mapleday.domain.character.model.CharacterBasic
import com.mosstis.mapleday.domain.character.model.CharacterData
import com.mosstis.mapleday.domain.character.model.CharacterScheduler
import com.mosstis.mapleday.domain.character.model.EventNotice
import com.mosstis.mapleday.domain.character.model.Inspection
import com.mosstis.mapleday.domain.character.model.SchedulerContent
import com.mosstis.mapleday.domain.character.model.TodayBlock
import com.mosstis.mapleday.domain.character.model.TodayData
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneId

/** NAS /app/today 응답(DTO) → 도메인 [TodayData]. */
internal fun TodayResponse.toDomain(now: Long): TodayData {
    // 캐릭터별: 성공한 블록만 변환, 실패면 null
    val characterMap = characters.mapValues { (ocid, dto) ->
        CharacterData(
            basicInfo = dto.basic.dataIfOk()?.toDomain(ocid, dto.basic.fetchedAtOr(now)),
            scheduler = dto.scheduler.dataIfOk()?.toDomain(ocid, dto.scheduler.fetchedAtOr(now)),
        )
    }

    // 이벤트: 성공이면 Ok, 실패면 Failed
    val eventsData = events.dataIfOk()
    val eventsBlock =
        if (eventsData != null) TodayBlock.Ok(eventsData.eventNotice.map { it.toDomain() })
        else TodayBlock.Failed

    // 점검: 성공했는데 점검이 없으면 Ok(null)
    val inspectionData = inspection.dataIfOk()
    val inspectionBlock =
        if (inspectionData != null) TodayBlock.Ok(inspectionData.inspection?.toDomain())
        else TodayBlock.Failed

    return TodayData(
        characters = characterMap,
        events = eventsBlock,
        inspection = inspectionBlock,
    )
}

private fun BasicDto.toDomain(ocid: String, fetchedAt: Long) = CharacterBasic(
    ocid = ocid,
    name = characterName,
    world = worldName,
    characterClass = characterClass,
    classLevel = characterClassLevel?.toIntOrNull(),
    level = characterLevel,
    exp = characterExp,
    expRate = characterExpRate?.toDoubleOrNull() ?: 0.0,
    guild = characterGuildName,
    imageUrl = characterImage,
    createdAt = characterDateCreate?.toMillisOrNull(),
    fetchedAt = fetchedAt,
)

private fun SchedulerDto.toDomain(ocid: String, fetchedAt: Long) = CharacterScheduler(
    ocid = ocid,
    date = date?.toKstDateOrNull() ?: LocalDate.now(KST),
    dailyContents = dailyContents.map { it.toDomain() },
    weeklyContents = weeklyContents.map { it.toDomain() },
    bossContents = bossContents.map { it.toDomain() },
    weeklyBossClearCount = weeklyBossClearCount,
    weeklyBossClearLimit = weeklyBossClearLimitCount,
    fetchedAt = fetchedAt,
)

// TODO: 스케줄러 항목 필드명·quest_state 완료 값은 콘텐츠를 등록한 캐릭터 응답으로 확인 필요
private val QUEST_COMPLETED = setOf("완료", "COMPLETE", "COMPLETED")

private fun SchedulerContentDto.toDomain() = SchedulerContent(
    name = contentName.orEmpty(),
    isRegistered = registrationFlag,
    nowCount = nowCount,
    maxCount = maxCount,
    isQuestCompleted = questState?.uppercase() in QUEST_COMPLETED,
)

private fun BossContentDto.toDomain() = BossContent(
    name = bossName.orEmpty(),
    difficulty = bossDifficulty.orEmpty(),
    isRegistered = registrationFlag,
    isCleared = clearFlag,
    cycle = if (cycle == "월간" || cycle.equals("MONTHLY", ignoreCase = true)) BossCycle.MONTHLY else BossCycle.WEEKLY,
)

private fun EventNoticeDto.toDomain() = EventNotice(
    id = noticeId,
    title = title,
    url = url,
    thumbnailUrl = thumbnailUrl,
    postedAt = date.toMillisOrNull() ?: 0L,
    startAt = dateEventStart?.toMillisOrNull(),
    endAt = dateEventEnd?.toMillisOrNull(),
)

/** 시각을 읽지 못하면 점검 정보를 버린다. */
private fun InspectionDto.toDomain(): Inspection? {
    val start = startDateTime.toMillisOrNull() ?: return null
    val end = endDateTime.toMillisOrNull() ?: return null
    return Inspection(title = title, serviceCode = serviceCode, startAt = start, endAt = end)
}

private val KST: ZoneId = ZoneId.of("Asia/Seoul")

// status가 "ok"면 data, 아니면 null
private fun <T> BlockDto<T>?.dataIfOk(): T? =
    if (this != null && isOk) data else null

// 블록의 fetched_at을 millis로, 없으면 now
private fun BlockDto<*>?.fetchedAtOr(now: Long): Long =
    this?.fetchedAt?.toMillisOrNull() ?: now

// ISO 시각 문자열 → epoch millis ("2026-10-22T07:39+09:00", "2026-10-27T13:21:54.033686+00:00")
private fun String.toMillisOrNull(): Long? =
    runCatching { OffsetDateTime.parse(this).toInstant().toEpochMilli() }.getOrNull()

// ISO 시각 문자열 → KST 기준 날짜 (+00:00으로 와도 하루 밀리지 않게)
private fun String.toKstDateOrNull(): LocalDate? =
    runCatching { OffsetDateTime.parse(this).atZoneSameInstant(KST).toLocalDate() }.getOrNull()
