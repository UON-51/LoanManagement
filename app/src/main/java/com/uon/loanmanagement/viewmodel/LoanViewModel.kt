package com.uon.loanmanagement.viewmodel

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.uon.loanmanagement.model.LoanDatabase
import com.uon.loanmanagement.model.LoanEntity
import com.uon.loanmanagement.repository.LoanRepository
import kotlinx.coroutines.launch


class LoanViewModel(application: Application): AndroidViewModel(application) {

    val loanDao = LoanDatabase.getDatabase(application).loanDao()
    private val loanRepository: LoanRepository by lazy {
        LoanRepository(loanDao)
    }

    var allLoans : LiveData<List<LoanEntity>> = loanRepository.allLoans
    val searchResult : MediatorLiveData<List<LoanEntity>> = loanRepository.searchResultMediator
    fun loanSearch(loanerName: String?, amount: Float?,dateStart:Long?,dateEnd:Long?, isPaid: Boolean?, searchTerm: String?){
        //TODO get loanerName by user selected spinner
        viewModelScope.launch {
            loanRepository.loanSearch(loanerName,amount,dateStart, dateEnd,isPaid,searchTerm)
        }
    }

    fun loanUpdate(loan: LoanEntity){
        viewModelScope.launch {
            loanRepository.loanUpdate(loan)
        }
    }

    fun loanDelete(loanIds: IntArray){
        viewModelScope.launch {
            loanRepository.loanDelete(loanIds)
        }
    }
    fun loanInsert(loan: LoanEntity){
        viewModelScope.launch {
            loanRepository.loanInsert(loan)
        }
    }






    fun loanInsertTest(){
        for (i in 0 .. 10){
            var testLoan = LoanEntity( loanerName = "johny$i", amount = 1000F * i, date = 1704751862380, isPaid = false, note = "Test$i")
            viewModelScope.launch {
                loanRepository.loanInsert(testLoan)
            }
        }
    }












}