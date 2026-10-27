package com.mosstis.mapleday.data.character.mapper

import com.mosstis.mapleday.core.network.nas.model.TodayResponse
import com.mosstis.mapleday.domain.character.model.TodayData

/** NAS /app/today 응답(DTO) → 도메인 [TodayData]. */
internal fun TodayResponse.toDomain(now: Long): TodayData {
    TODO()
}
