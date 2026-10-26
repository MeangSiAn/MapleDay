package com.mosstis.mapleday.domain.user.repository

import com.mosstis.mapleday.domain.user.model.AccountCharacter

interface AccountRepository {

    /** 사용자 키로 계정 캐릭터 목록을 불러온다. 실패하면 `NexonApiError`를 던진다. */
    suspend fun fetchAccountCharacters(apiKey: String): List<AccountCharacter>

    /** 저장된 키로 캐릭터 목록을 불러온다. 연결된 계정이 없으면 null. 키는 data 레이어 밖으로 꺼내지 않는다. */
    suspend fun fetchLinkedAccountCharacters(): List<AccountCharacter>?

    /** 사용자 키를 암호화해 저장한다. 이미 있으면 덮어쓴다. */
    suspend fun saveApiKey(apiKey: String)
}
