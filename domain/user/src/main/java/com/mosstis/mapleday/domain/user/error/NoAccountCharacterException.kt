package com.mosstis.mapleday.domain.user.error

/** 키는 유효하지만 계정에 캐릭터가 없다 (SPEC SC2 "캐릭터 0개"). */
class NoAccountCharacterException : Exception("NO_ACCOUNT_CHARACTER")
