package com.mosstis.mapleday.domain.user.usecase

import com.mosstis.mapleday.core.common.error.NexonApiError
import com.mosstis.mapleday.domain.user.error.NoAccountCharacterException
import com.mosstis.mapleday.domain.user.model.AccountCharacter
import com.mosstis.mapleday.domain.user.repository.AccountRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.fail
import org.junit.Test

class LinkAccountUseCaseTest {

    private class FakeAccountRepository(
        var characters: List<AccountCharacter> = emptyList(),
        var error: Throwable? = null,
    ) : AccountRepository {
        var fetchedKey: String? = null
        var savedKey: String? = null

        override suspend fun fetchAccountCharacters(apiKey: String): List<AccountCharacter> {
            fetchedKey = apiKey
            error?.let { throw it }
            return characters
        }

        override suspend fun fetchLinkedAccountCharacters(): List<AccountCharacter>? =
            savedKey?.let { fetchAccountCharacters(it) }

        override suspend fun saveApiKey(apiKey: String) {
            savedKey = apiKey
        }
    }

    private fun character(name: String, level: Int) =
        AccountCharacter(ocid = name, name = name, world = "스카니아", characterClass = "보우마스터", level = level)

    @Test
    fun `성공하면 키를 저장하고 레벨 내림차순으로 돌려준다`() = runTest {
        val repo = FakeAccountRepository(characters = listOf(character("a", 200), character("b", 270)))

        val result = LinkAccountUseCase(repo)("  live_key  ")

        assertEquals(listOf(270, 200), result.map { it.level })
        assertEquals("live_key", repo.fetchedKey)
        assertEquals("live_key", repo.savedKey)
    }

    @Test
    fun `캐릭터가 0개면 예외이고 키를 저장하지 않는다`() = runTest {
        val repo = FakeAccountRepository(characters = emptyList())

        try {
            LinkAccountUseCase(repo)("live_key")
            fail("NoAccountCharacterException이 나야 한다")
        } catch (_: NoAccountCharacterException) {
        }
        assertNull(repo.savedKey)
    }

    @Test
    fun `넥슨 오류는 그대로 전달하고 키를 저장하지 않는다`() = runTest {
        val repo = FakeAccountRepository(error = NexonApiError.InvalidApiKey())

        try {
            LinkAccountUseCase(repo)("bad")
            fail("InvalidApiKey가 나야 한다")
        } catch (_: NexonApiError.InvalidApiKey) {
        }
        assertNull(repo.savedKey)
    }
}
