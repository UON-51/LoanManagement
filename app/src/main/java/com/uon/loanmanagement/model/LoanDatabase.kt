package com.uon.loanmanagement.model

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LoanEntity::class] , version = 1 )
abstract class LoanDatabase: RoomDatabase() {

    //create a singleton database
    companion object {
        private var INSTANCE: LoanDatabase? = null
        fun getDatabase(context: Context): LoanDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context,LoanDatabase::class.java, "loan_database")
                            .build()
                }
            }
            Log.d("Room","database create success")
            return INSTANCE!!
        }
    }
    abstract fun loanDao() : LoanDao
}