package com.uon.loanmanagement.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Loan")
data class LoanEntity(
    @PrimaryKey(autoGenerate = true) val loanId: Int = 0,
    val loanerName: String,
    val amount:Float,
    val date: Long,
    val isPaid: Boolean,
    val note: String
)
