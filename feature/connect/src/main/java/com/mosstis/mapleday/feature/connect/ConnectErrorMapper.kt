package com.mosstis.mapleday.feature.connect

import androidx.annotation.StringRes
import com.mosstis.mapleday.core.common.error.NexonApiError
import com.mosstis.mapleday.domain.user.error.NoAccountCharacterException
import com.mosstis.mapleday.feature.connect.ConnectContract.ConnectError

/** 계정 연결 중 난 예외를 화면 오류로 바꾼다 (SPEC S04: 네트워크 오류와 키 오류 문구가 다르다). */
internal fun Throwable.toConnectError(): ConnectError = when (this) {
    is NexonApiError.InvalidApiKey -> ConnectError.InvalidKey
    is NexonApiError.Forbidden -> ConnectError.Forbidden
    is NexonApiError.Network -> ConnectError.Network
    is NexonApiError.RateLimited -> ConnectError.RateLimited
    is NexonApiError.Maintenance -> ConnectError.Maintenance
    is NexonApiError.DataPreparing -> ConnectError.DataPreparing
    is NoAccountCharacterException -> ConnectError.NoCharacter
    // NotFound(ocid)는 계정 연결에선 나오지 않는다
    else -> ConnectError.Unknown
}

@StringRes
internal fun ConnectError.messageRes(): Int = when (this) {
    ConnectError.InvalidKey -> R.string.connect_nexon_api_key_error
    ConnectError.Forbidden -> R.string.connect_error_forbidden
    ConnectError.NoCharacter -> R.string.connect_error_no_character
    ConnectError.Network -> R.string.connect_error_network
    ConnectError.RateLimited -> R.string.connect_error_rate_limited
    ConnectError.Maintenance -> R.string.connect_error_maintenance
    ConnectError.DataPreparing -> R.string.connect_error_data_preparing
    ConnectError.Unknown -> R.string.connect_error_unknown
}
