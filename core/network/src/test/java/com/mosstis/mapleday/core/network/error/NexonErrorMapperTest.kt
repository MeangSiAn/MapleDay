package com.mosstis.mapleday.core.network.error

import com.mosstis.mapleday.core.common.error.NexonApiError
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class NexonErrorMapperTest {

    private fun httpException(status: Int, body: String) = HttpException(
        Response.error<Any>(status, body.toResponseBody("application/json".toMediaType())),
    )

    private fun errorBody(name: String) = """{"error":{"name":"$name","message":"msg"}}"""

    private suspend fun capture(block: suspend () -> Unit): Throwable {
        try {
            nexonCall(block)
        } catch (e: Throwable) {
            return e
        }
        fail("예외가 나야 한다")
        error("unreachable")
    }

    @Test
    fun `잘못된 키는 InvalidApiKey`() = runTest {
        val e = capture { throw httpException(400, errorBody("OPENAPI00005")) }
        assertTrue(e is NexonApiError.InvalidApiKey)
    }

    @Test
    fun `권한 없음은 Forbidden`() = runTest {
        val e = capture { throw httpException(403, errorBody("OPENAPI00002")) }
        assertTrue(e is NexonApiError.Forbidden)
    }

    @Test
    fun `이름 없는 429는 RateLimited`() = runTest {
        val e = capture { throw httpException(429, "Too Many Requests") }
        assertTrue(e is NexonApiError.RateLimited)
    }

    @Test
    fun `점검은 Maintenance로 이름을 보존`() = runTest {
        val e = capture { throw httpException(503, errorBody("OPENAPI00011")) }
        assertEquals("OPENAPI00011", (e as NexonApiError.Maintenance).errorName)
    }

    @Test
    fun `알 수 없는 본문은 Unknown`() = runTest {
        val e = capture { throw httpException(500, "<html>oops</html>") }
        e as NexonApiError.Unknown
        assertNull(e.errorName)
        assertEquals(500, e.httpStatus)
    }

    @Test
    fun `IOException은 Network`() = runTest {
        val cause = IOException("offline")
        val e = capture { throw cause }
        assertTrue(e is NexonApiError.Network)
        assertEquals(cause, e.cause)
    }

    @Test
    fun `성공은 그대로 반환`() = runTest {
        assertEquals(1, nexonCall { 1 })
    }
}
