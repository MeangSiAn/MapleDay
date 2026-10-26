package com.mosstis.mapleday.core.security

/** AES/GCM 암호화 결과. 복호화하려면 [cipherText]와 [iv]가 모두 필요하다. */
class EncryptedData(
    val cipherText: ByteArray,
    val iv: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EncryptedData) return false
        return cipherText.contentEquals(other.cipherText) && iv.contentEquals(other.iv)
    }

    override fun hashCode(): Int = 31 * cipherText.contentHashCode() + iv.contentHashCode()
}
