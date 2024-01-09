package com.uon.loanmanagement

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.uon.loanmanagement.model.LoanDao
import com.uon.loanmanagement.model.LoanDatabase
import com.uon.loanmanagement.model.LoanEntity
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoanDatabaseTest {
    private lateinit var loanDatabase: LoanDatabase
    private lateinit var loanDao : LoanDao

    @Before
    fun createDB(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        loanDatabase = Room.inMemoryDatabaseBuilder(context.applicationContext,LoanDatabase::class.java).build()
        loanDao = loanDatabase.loanDao()
    }

    @After
    fun closeDB(){
        loanDatabase.close()
    }

    @Test
    fun updateTest(){
        var testLoan = LoanEntity(loanId = 3, loanerName = "johny", amount = 1000F, date = 1704751862380, isPaid = false, note = "Test")
        loanDao.loanInsert(testLoan)
        testLoan = LoanEntity(loanId = 3, loanerName = "johnny", amount = 1000F, date = 1704751862380, isPaid = false, note = "Test")
        val success = loanDao.updateLoan(testLoan)
        Assert.assertEquals(0,success)

    }




}