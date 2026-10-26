package com.mosstis.mapleday.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 기록하기로 고른 내 캐릭터 (S05 저장). 한 명이라도 있으면 스플래시 이후 메인으로 간다.
 */
@Entity(tableName = "character")
data class CharacterEntity(
    @PrimaryKey
    @ColumnInfo(name = "ocid")
    val ocid: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "world")
    val world: String,
    @ColumnInfo(name = "character_class")
    val characterClass: String,
    @ColumnInfo(name = "level")
    val level: Int,
    /** 추가 시각 (epoch millis) */
    @ColumnInfo(name = "added_at")
    val addedAt: Long,
)
