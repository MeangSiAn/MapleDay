package com.mosstis.mapleday.core.security

/**
 * 사용자 넥슨 API 키 암호화/복호화 (SPEC SC2 "키는 이 기기에만 암호화해 저장").
 */
interface UserKeyStore {
    fun encrypt(plainText: String): EncryptedData

    fun decrypt(data: EncryptedData): String
}
