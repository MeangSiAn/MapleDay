package com.mosstis.mapleday.domain.user.usecase

import com.mosstis.mapleday.domain.user.repository.UserKeyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckUserKeyUseCase @Inject constructor(
    private val repository : UserKeyRepository
) {
    operator fun invoke() : Flow<Boolean> {
        return repository.getUserKey()
    }
}