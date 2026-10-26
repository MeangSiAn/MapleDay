package com.mosstis.mapleday.core.common.error

/**
 * 넥슨 Open API 오류 (SPEC SC11). 사용자 문구는 화면(ViewModel)이 정한다.
 * 메시지에는 오류 이름만 담고 키·응답 본문은 담지 않는다.
 */
sealed class NexonApiError(
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause) {

    /** OPENAPI00005: 유효하지 않은 API 키 */
    class InvalidApiKey : NexonApiError("OPENAPI00005")

    /** OPENAPI00002: 권한 없음 (메이플스토리용 키가 아님 등) */
    class Forbidden : NexonApiError("OPENAPI00002")

    /** OPENAPI00003: 유효하지 않은 식별자 (ocid·이름 없음) */
    class NotFound : NexonApiError("OPENAPI00003")

    /** OPENAPI00009: 데이터 준비 중 */
    class DataPreparing : NexonApiError("OPENAPI00009")

    /** 429, OPENAPI00007: 호출량 초과 */
    class RateLimited : NexonApiError("OPENAPI00007")

    /** OPENAPI00010(게임 점검), OPENAPI00011(API 점검) */
    class Maintenance(val errorName: String) : NexonApiError(errorName)

    /** 응답을 받지 못함 (오프라인, 타임아웃) */
    class Network(cause: Throwable) : NexonApiError("NETWORK", cause)

    /** 그 밖의 오류. [errorName]은 본문에 오류 이름이 없으면 null */
    class Unknown(val errorName: String?, val httpStatus: Int) :
        NexonApiError("${errorName ?: "UNKNOWN"} ($httpStatus)")
}
