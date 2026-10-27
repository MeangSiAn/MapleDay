package com.mosstis.mapleday.data.character.mapper

import com.mosstis.mapleday.core.network.nas.model.TodayResponse
import com.mosstis.mapleday.domain.character.model.TodayBlock
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

/** 실제 NAS /app/today 응답(2026-10-27)을 줄인 샘플로 DTO 파싱 + 도메인 변환을 확인한다. */
class TodayDtoMapperTest {

    private val json = Json { ignoreUnknownKeys = true; explicitNulls = false }

    private val sample = """
        {
          "characters": {
            "e6b0": {
              "basic": {
                "status": "ok", "cache": "MISS", "fetched_at": "2026-10-27T13:21:54.033686+00:00",
                "data": {
                  "date": null, "character_name": "맹리엔", "world_name": "크로아", "character_gender": "남",
                  "character_class": "렌", "character_class_level": "6", "character_level": 272,
                  "character_exp": 1254292389713, "character_exp_rate": "22.718", "character_guild_name": null,
                  "character_image": "https://open.api.nexon.com/static/x?wmotion=W00",
                  "character_date_create": "2025-06-19T00:00+09:00", "access_flag": "false", "liberation_quest_clear": "0"
                }
              },
              "scheduler": {
                "status": "ok", "cache": "MISS", "fetched_at": "2026-10-27T13:21:54.306642+00:00",
                "data": {
                  "date": "2026-10-27T00:00+09:00", "character_name": "맹리엔", "world_name": "크로아",
                  "character_level": 272, "character_class": "렌",
                  "daily_contents": [], "weekly_contents": [], "boss_contents": [],
                  "weekly_boss_clear_count": 0, "weekly_boss_clear_limit_count": 0
                }
              }
            },
            "fail": { "basic": { "status": "error" } }
          },
          "events": {
            "status": "ok", "cache": "HIT", "fetched_at": "2026-10-27T13:18:55.764566+00:00",
            "data": { "event_notice": [ {
              "url": "https://maplestory.nexon.com/News/Event/1404", "date": "2026-10-22T07:39+09:00",
              "title": "메이플 이스케이프: 사라진 네미", "notice_id": 1404, "thumbnail_url": "https://file.nexon.com/x",
              "date_event_end": "2026-11-18T23:59+09:00", "date_event_start": "2026-10-22T10:00+09:00"
            } ] }
          },
          "inspection": {
            "status": "ok", "cache": "HIT", "fetched_at": "2026-10-27T13:18:55.377226+00:00",
            "data": { "inspection": {
              "title": "[패치중] 10/22(목) 클라이언트 패치(04:00~10:00)", "service_code": 2,
              "end_date_time": "2026-10-22T10:00:00+09:00", "start_date_time": "2026-10-22T04:00:00+09:00"
            } }
          }
        }
    """.trimIndent()

    private val today = json.decodeFromString<TodayResponse>(sample).toDomain(now = 0L)

    @Test
    fun `basic을 도메인으로 변환한다`() {
        val basic = requireNotNull(today.characters.getValue("e6b0").basicInfo)
        assertEquals("맹리엔", basic.name)
        assertEquals(6, basic.classLevel)
        assertEquals(272, basic.level)
        assertEquals(1254292389713L, basic.exp)
        assertEquals(22.718, basic.expRate, 0.0)
        assertNull(basic.guild)
        // 2026-10-27T13:21:54.033686Z
        assertEquals(1793107314033L, basic.fetchedAt)
    }

    @Test
    fun `scheduler 기준일은 KST 날짜다`() {
        val scheduler = requireNotNull(today.characters.getValue("e6b0").scheduler)
        assertEquals(LocalDate.of(2026, 10, 27), scheduler.date)
        assertTrue(scheduler.dailyContents.isEmpty())
    }

    @Test
    fun `캐릭터 블록이 실패하거나 없으면 null`() {
        val failed = today.characters.getValue("fail")
        assertNull(failed.basicInfo)
        assertNull(failed.scheduler)
    }

    @Test
    fun `이벤트와 점검을 변환한다`() {
        val events = (today.events as TodayBlock.Ok).data
        assertEquals(1404L, events.single().id)
        val inspection = (today.inspection as TodayBlock.Ok).data
        assertEquals(2, inspection?.serviceCode)
    }
}
