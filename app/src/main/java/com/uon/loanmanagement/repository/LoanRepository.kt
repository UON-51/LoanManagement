package com.uon.loanmanagement.repository


import android.util.Log
import androidx.lifecycle.MediatorLiveData
import com.uon.loanmanagement.model.LoanDao
import com.uon.loanmanagement.model.LoanEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class LoanRepository(private val loanDao : LoanDao) {


    //val allLoans: LiveData<List<LoanEntity>> = loanDao.getAllLoan() //get all loan data when repository init
    val searchResultMediator = MediatorLiveData<List<LoanEntity>>() //create a mediatorLiveData for storage search result


    //update loan record function
    suspend fun loanUpdate(loan: LoanEntity) {
        withContext(Dispatchers.IO) {
           if (loanDao.updateLoan(loan)==1){
               Log.d("Room","update success")
           }
        }

    }

    //insert loan record function
    suspend fun loanInsert(loan: LoanEntity) {
        withContext(Dispatchers.IO) {
            loanDao.loanInsert(loan)
        }
    }

    //delete loan record function
    suspend fun loanDelete(loanIds: IntArray) {
        withContext(Dispatchers.IO) {
            loanDao.loanDelete(loanIds)
        }
    }

    //search loan function
    suspend fun loanSearch(loanerName: String?, amount: Float?,dateStart:Long?,dateEnd:Long?, isPaid: Boolean?, searchTerm: String?) {
        withContext(Dispatchers.IO) {
            val searchResult = loanDao.searchLoans(loanerName,amount,dateStart, dateEnd,isPaid,searchTerm)

            searchResultMediator.addSource(searchResult){
                    result ->
                searchResultMediator.postValue(result)
            }
        }
    }
}