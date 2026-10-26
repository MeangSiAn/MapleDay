package com.mosstis.mapleday.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 넥슨 계정 연결 정보 (SPEC SC2). 계정은 기기당 1개라 PK를 [ACCOUNT_ID]로 고정한다.
 * API 키는 평문으로 저장하지 않고 Keystore로 암호화한 암호문과 IV만 저장한다.
 */
@Entity(tableName = "account")
class AccountEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = ACCOUNT_ID,
    @ColumnInfo(name = "encrypted_api_key", typeAffinity = ColumnInfo.BLOB)
    val encryptedApiKey: ByteArray,
    @ColumnInfo(name = "iv", typeAffinity = ColumnInfo.BLOB)
    val iv: ByteArray,
    /** 연결 시각 (epoch millis) */
    @ColumnInfo(name = "linked_at")
    val linkedAt: Long,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AccountEntity) return false
        return id == other.id &&
            encryptedApiKey.contentEquals(other.encryptedApiKey) &&
            iv.contentEquals(other.iv) &&
            linkedAt == other.linkedAt
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + encryptedApiKey.contentHashCode()
        result = 31 * result + iv.contentHashCode()
        result = 31 * result + linkedAt.hashCode()
        return result
    }

    companion object {
        const val ACCOUNT_ID = 1
    }
}
