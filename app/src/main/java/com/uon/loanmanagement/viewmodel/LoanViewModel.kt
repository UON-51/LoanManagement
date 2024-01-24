package com.uon.loanmanagement.viewmodel

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.uon.loanmanagement.model.LoanDatabase
import com.uon.loanmanagement.model.LoanEntity
import com.uon.loanmanagement.repository.LoanRepository
import kotlinx.coroutines.launch


class LoanViewModel(application: Application): AndroidViewModel(application) {

    private val loanRepository: LoanRepository by lazy {
        LoanRepository(loanDao)
    }

    val nowTitle: MutableLiveData<String> = MutableLiveData("AllRecord") //todo get from loanerName
    private val loanDao = LoanDatabase.getDatabase(application).loanDao()
    val selectedLoan : MutableLiveData<LoanEntity> = MutableLiveData()
    private val _searchSetting : MutableLiveData<LoanEntity> = MutableLiveData()
    private val _isInDefault : MutableLiveData<Boolean> = MutableLiveData(true)
    val isInDefault : LiveData<Boolean> get() = _isInDefault





    private val _searchResult : MediatorLiveData<List<LoanEntity>> = loanRepository.searchResultMediator
    val searchResult : LiveData<List<LoanEntity>> get() = _searchResult



    init {
        searchResultObserving()
    }

    fun loanSearch(loanerName: String?, amount: Float?,dateStart:Long?,dateEnd:Long?, isPaid: Boolean?, searchTerm: String?){
        //TODO finish search function link
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

    fun loanInsert(loanId:String?,loanerName :String, amount :Float, date :Long, isPaid :Boolean, note:String){
        val loan2Insert : LoanEntity
        if  (loanId.isNullOrEmpty()){
            loan2Insert = LoanEntity(loanerName = loanerName, amount = amount, date = date, isPaid = isPaid, note = note)
        } else {
            loan2Insert = LoanEntity(loanId = loanId.toInt(),loanerName = loanerName, amount = amount, date = date, isPaid = isPaid, note = note)
        }

        viewModelScope.launch {
            loanRepository.loanInsert(loan2Insert)
        }

    }

    //todo add function to clear selectedLoan

    fun loanSelected(loan : LoanEntity){
        selectedLoan.value = loan
    }

    fun getSearchSetting(){

    }

    fun searchResultObserving(){
        viewModelScope.launch {
            loanRepository.loanSearch(null,null,null,null,null,null)
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
    fun loanUpdateTest(){
        var testLoan = LoanEntity(loanId=5, loanerName="johny4", amount=4000F, date=1704751862380, isPaid=false, note="EditTest4")
        viewModelScope.launch {
            loanRepository.loanInsert(testLoan)
        }
    }












}