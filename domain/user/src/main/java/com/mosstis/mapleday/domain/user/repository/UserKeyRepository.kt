package com.mosstis.mapleday.domain.user.repository

import kotlinx.coroutines.flow.Flow


interface UserKeyRepository {
    fun getUserKey() : Flow<Boolean>
}