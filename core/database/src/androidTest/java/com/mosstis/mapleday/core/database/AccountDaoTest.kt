package com.mosstis.mapleday.core.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mosstis.mapleday.core.database.dao.AccountDao
import com.mosstis.mapleday.core.database.entity.AccountEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AccountDaoTest {

    private lateinit var database: MapleDayDatabase
    private lateinit var dao: AccountDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MapleDayDatabase::class.java,
        ).build()
        dao = database.accountDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun upsert_후_get과_observe가_같은_계정을_반환한다() = runTest {
        val account = account(cipher = byteArrayOf(1, 2, 3), linkedAt = 100L)

        dao.upsert(account)

        assertEquals(account, dao.get())
        assertEquals(account, dao.observe().first())
    }

    @Test
    fun 다시_upsert하면_한_행을_덮어쓴다() = runTest {
        dao.upsert(account(cipher = byteArrayOf(1), linkedAt = 100L))
        val relinked = account(cipher = byteArrayOf(9, 9), linkedAt = 200L)

        dao.upsert(relinked)

        assertEquals(relinked, dao.get())
    }

    @Test
    fun clear_후에는_계정이_없다() = runTest {
        dao.upsert(account(cipher = byteArrayOf(1), linkedAt = 100L))

        dao.clear()

        assertNull(dao.get())
        assertNull(dao.observe().first())
    }

    private fun account(cipher: ByteArray, linkedAt: Long) = AccountEntity(
        encryptedApiKey = cipher,
        iv = byteArrayOf(7, 7, 7),
        linkedAt = linkedAt,
    )
}
