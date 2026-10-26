package com.mosstis.mapleday.core.security

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class KeystoreUserKeyStoreTest {

    private val keyStore = KeystoreUserKeyStore()

    @Test
    fun 암호화한_키를_복호화하면_원문과_같다() {
        val apiKey = "test_abc123가나다"

        val decrypted = keyStore.decrypt(keyStore.encrypt(apiKey))

        assertEquals(apiKey, decrypted)
    }

    @Test
    fun 같은_원문도_매번_다른_IV와_암호문이_나온다() {
        val first = keyStore.encrypt("same-key")
        val second = keyStore.encrypt("same-key")

        assertFalse(first.iv.contentEquals(second.iv))
        assertFalse(first.cipherText.contentEquals(second.cipherText))
    }

    @Test
    fun 암호문에_원문이_그대로_들어있지_않다() {
        val apiKey = "plain-api-key"

        val encrypted = keyStore.encrypt(apiKey)

        assertFalse(encrypted.cipherText.toString(Charsets.UTF_8).contains(apiKey))
    }
}
