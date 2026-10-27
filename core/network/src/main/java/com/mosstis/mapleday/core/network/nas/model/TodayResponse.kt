package com.mosstis.mapleday.core.network.nas.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** GET /app/today 응답. 블록마다 성공·실패가 따로라 [BlockDto]로 감싼다. */
@Serializable
data class TodayResponse(
    /** key = ocid */
    @SerialName("characters") val characters: Map<String, CharacterTodayDto> = emptyMap(),
    @SerialName("events") val events: BlockDto<EventsDto>? = null,
    @SerialName("inspection") val inspection: BlockDto<InspectionWrapperDto>? = null,
)

@Serializable
data class CharacterTodayDto(
    @SerialName("basic") val basic: BlockDto<BasicDto>? = null,
    @SerialName("scheduler") val scheduler: BlockDto<SchedulerDto>? = null,
)

/** 블록 하나. `status`가 ok가 아니면 실패 (data 없음). */
@Serializable
data class BlockDto<T>(
    @SerialName("status") val status: String,
    @SerialName("fetched_at") val fetchedAt: String? = null,
    @SerialName("data") val data: T? = null,
) {
    val isOk: Boolean get() = status == STATUS_OK && data != null

    companion object {
        const val STATUS_OK = "ok"
    }
}

@Serializable
data class BasicDto(
    @SerialName("character_name") val characterName: String,
    @SerialName("world_name") val worldName: String,
    @SerialName("character_class") val characterClass: String,
    @SerialName("character_class_level") val characterClassLevel: String? = null,
    @SerialName("character_level") val characterLevel: Int,
    @SerialName("character_exp") val characterExp: Long = 0,
    @SerialName("character_exp_rate") val characterExpRate: String? = null,
    @SerialName("character_guild_name") val characterGuildName: String? = null,
    @SerialName("character_image") val characterImage: String? = null,
    @SerialName("character_date_create") val characterDateCreate: String? = null,
)

@Serializable
data class SchedulerDto(
    @SerialName("date") val date: String? = null,
    @SerialName("daily_contents") val dailyContents: List<SchedulerContentDto> = emptyList(),
    @SerialName("weekly_contents") val weeklyContents: List<SchedulerContentDto> = emptyList(),
    @SerialName("boss_contents") val bossContents: List<BossContentDto> = emptyList(),
    @SerialName("weekly_boss_clear_count") val weeklyBossClearCount: Int = 0,
    @SerialName("weekly_boss_clear_limit_count") val weeklyBossClearLimitCount: Int = 0,
)

// TODO: 콘텐츠를 등록한 캐릭터의 실제 응답으로 필드명 확인. 지금은 SPEC H01·P01 기준 추정이라 모두 기본값을 둔다.
@Serializable
data class SchedulerContentDto(
    @SerialName("content_name") val contentName: String? = null,
    @SerialName("registration_flag") val registrationFlag: Boolean = false,
    @SerialName("now_count") val nowCount: Int = 0,
    @SerialName("max_count") val maxCount: Int = 0,
    @SerialName("quest_state") val questState: String? = null,
)

// TODO: 위와 같음 (보스 필드명 추정)
@Serializable
data class BossContentDto(
    @SerialName("boss_name") val bossName: String? = null,
    @SerialName("boss_difficulty") val bossDifficulty: String? = null,
    @SerialName("registration_flag") val registrationFlag: Boolean = false,
    @SerialName("clear_flag") val clearFlag: Boolean = false,
    @SerialName("cycle") val cycle: String? = null,
)

@Serializable
data class EventsDto(
    @SerialName("event_notice") val eventNotice: List<EventNoticeDto> = emptyList(),
)

@Serializable
data class EventNoticeDto(
    @SerialName("notice_id") val noticeId: Long,
    @SerialName("title") val title: String,
    @SerialName("url") val url: String,
    @SerialName("thumbnail_url") val thumbnailUrl: String? = null,
    @SerialName("date") val date: String,
    @SerialName("date_event_start") val dateEventStart: String? = null,
    @SerialName("date_event_end") val dateEventEnd: String? = null,
)

/** 예정된 점검이 없으면 [inspection]이 null. */
@Serializable
data class InspectionWrapperDto(
    @SerialName("inspection") val inspection: InspectionDto? = null,
)

@Serializable
data class InspectionDto(
    @SerialName("title") val title: String,
    @SerialName("service_code") val serviceCode: Int = 0,
    @SerialName("start_date_time") val startDateTime: String,
    @SerialName("end_date_time") val endDateTime: String,
)
