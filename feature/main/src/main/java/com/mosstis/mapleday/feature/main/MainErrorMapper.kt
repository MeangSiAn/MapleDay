package com.mosstis.mapleday.feature.main

import com.mosstis.mapleday.core.common.error.NasApiError
import com.mosstis.mapleday.feature.main.MainContract.MainError

internal fun Throwable.toMainError(): MainError = when (this) {
    is NasApiError.TokenError -> MainError.TokenError
    is NasApiError.Network -> MainError.Network
    else -> MainError.Unknown
}

internal fun MainError.messageRes() : Int = when (this) {
    MainError.TokenError -> R.string.main_error_token
    MainError.Network -> R.string.main_error_network
    else -> R.string.main_error_unknown
}
