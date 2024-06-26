package com.uon.loanmanagement.repository


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.uon.loanmanagement.model.LoanDao
import com.uon.loanmanagement.model.LoanEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class LoanRepository(private val loanDao : LoanDao) {
    val searchResultMediator = MediatorLiveData<List<LoanEntity>>() //create a mediatorLiveData for storage search result
    private lateinit var _searchResult : LiveData<List<LoanEntity>>
    private var _isInit = false
    val distinctLoanerNamesMediator = MediatorLiveData<List<String>>()


    //function for insert loan record
    suspend fun loanInsert(loan: LoanEntity) {
        withContext(Dispatchers.IO) {
            loanDao.loanInsert(loan)
            Log.d("searchTest","loanInsert")
        }
    }

    //function for get all distinct loaner name
    suspend fun getDistinctLoanerNames(){
        withContext(Dispatchers.IO){
            val distinctLoanerNames = loanDao.getDistinctLoanerNames()
            distinctLoanerNamesMediator.addSource(distinctLoanerNames){
                    result ->
                distinctLoanerNamesMediator.postValue(result)
            }
        }
    }

    //function for delete loan record
    suspend fun loanDelete(loanId: Int) {
        withContext(Dispatchers.IO) {
            loanDao.loanDelete(loanId)
            Log.d("searchTest","loanDelete")
        }
    }

    //function for search loan record
    suspend fun loanSearch(loanerName: String?, amountStart: Float?,amountEnd: Float?,dateStart:Long?,dateEnd:Long?, isPaid: Boolean?, searchTerm: String?) {
        if (_isInit){
            searchResultMediator.removeSource(_searchResult)
        }

        withContext(Dispatchers.Main) {
            _searchResult = loanDao.searchLoans(loanerName, amountStart, amountEnd, dateStart, dateEnd, isPaid, searchTerm)
            searchResultMediator.addSource(_searchResult){
                    result ->
                searchResultMediator.postValue(result)
                Log.d("searchTest","searchResultMediator.postValue($result)")
            }
        }
        _isInit = true
    }
}