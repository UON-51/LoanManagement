package com.uon.loanmanagement.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface LoanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun loanInsert(loan: LoanEntity)

    @Query("DELETE FROM Loan WHERE loanId IN (:loanId)")
    fun loanDelete(loanId: Int)

    @Query("SELECT DISTINCT loanerName FROM Loan")
    fun getDistinctLoanerNames(): LiveData<List<String>>

    @Update
    fun updateLoan(loan : LoanEntity):Int


    @Query("SELECT * FROM Loan WHERE " +
            "(loanerName LIKE :loanerName OR :loanerName IS NULL) " +
            "AND ((amount BETWEEN :amountStart AND :amountEnd) OR (amount >= :amountStart AND :amountEnd IS NULL) OR (amount <= :amountEnd AND :amountStart IS NULL) OR (:amountStart IS NULL AND :amountEnd IS NULL)) " +
            "AND ((date BETWEEN :dateStart AND :dateEnd) OR (date >= :dateStart AND :dateEnd IS NULL) OR (date <= :dateEnd AND :dateStart IS NULL) OR (:dateStart IS NULL AND :dateEnd IS NULL)) " +
            "AND (isPaid = :isPaid OR :isPaid IS NULL) " +
            "AND (note LIKE '%' || :searchTerm || '%' OR :searchTerm IS NULL)" +
            "ORDER BY loanId" )
    fun searchLoans(loanerName: String?, amountStart: Float?, amountEnd: Float?,dateStart:Long?,dateEnd:Long?, isPaid: Boolean?, searchTerm: String?):LiveData<List<LoanEntity>>




}