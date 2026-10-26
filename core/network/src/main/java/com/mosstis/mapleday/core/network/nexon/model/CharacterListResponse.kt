package com.mosstis.mapleday.core.network.nexon.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterListResponse(
    @SerialName("account_list") val accountList: List<AccountDto> = emptyList(),
)

@Serializable
data class AccountDto(
    @SerialName("account_id") val accountId: String,
    @SerialName("character_list") val characterList: List<CharacterDto> = emptyList(),
)

@Serializable
data class CharacterDto(
    @SerialName("ocid") val ocid: String,
    @SerialName("character_name") val characterName: String,
    @SerialName("world_name") val worldName: String,
    @SerialName("character_class") val characterClass: String,
    @SerialName("character_level") val characterLevel: Int,
)
