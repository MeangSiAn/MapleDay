package com.mosstis.mapleday.domain.character.model

/**
 * 화면에 뿌를 캐릭터 요약 정보
 */
data class TodayOverview(
    /** HeroCard. 아직 받은 적 없으면 null (스켈레톤) */
    val basic: CharacterBasic?,
    /** 주간 보스 n/한도 */
    val scheduler: CharacterScheduler?,
    /** 오늘 숙제: 등록한 일일 콘텐츠, 미완료 먼저 */
    val todayTasks: List<SchedulerContent>,
    /** 곧 끝나요: 7일 이내 종료 이벤트, 종료 임박 순, 최대 3개 */
    val endingSoonEvents: List<EventNotice>,
    /** 예정되었거나 진행 중인 점검. 이미 끝났으면 null */
    val inspection: Inspection?,
)
