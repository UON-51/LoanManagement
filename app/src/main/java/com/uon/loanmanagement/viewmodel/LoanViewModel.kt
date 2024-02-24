package com.uon.loanmanagement.viewmodel

import android.app.Application
import android.util.Log

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.uon.loanmanagement.model.LoanDatabase
import com.uon.loanmanagement.model.LoanEntity
import com.uon.loanmanagement.repository.LoanRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat


class LoanViewModel(application: Application): AndroidViewModel(application) {
    private val loanRepository: LoanRepository by lazy {
        LoanRepository(loanDao)
    }

    private val _nowTitle: MutableLiveData<String> = MutableLiveData("AllRecord") //todo get from loanerName
    val nowTitle: LiveData<String> get() = _nowTitle
    private val loanDao = LoanDatabase.getDatabase(application).loanDao()
    val selectedLoan : MutableLiveData<LoanEntity> = MutableLiveData()

    private val _navigateToMainFragment : MutableLiveData<Boolean> = MutableLiveData(true)
    val navigateToMainFragment : LiveData<Boolean> get() = _navigateToMainFragment
    private val _isInDefault : MutableLiveData<Boolean> = MutableLiveData(true)
    val isInDefault : LiveData<Boolean> get() = _isInDefault
    private val _insertFieldsIsEmpty : MutableLiveData<Boolean> = MutableLiveData(false)
    val insertFieldsIsEmpty : LiveData<Boolean> get() = _insertFieldsIsEmpty

    private val _searchResult : MediatorLiveData<List<LoanEntity>> = loanRepository.searchResultMediator
    val searchResult : LiveData<List<LoanEntity>> get() = _searchResult
    private val _distinctLoanerName : MediatorLiveData<List<String>> = loanRepository.distinctLoanerNamesMediator
    val distinctLoanerName : LiveData<List<String>> get() = _distinctLoanerName

    init {
        searchResultInit()
        distinctLoanerNamesInit()
        /*
        isInDefault.observeForever {
            if (it){
                searchResultInit()
            }
        }
         */
    }

    fun loanSearch(loanerName: String?, amountStartString: String?,amountEndString: String?,dateStartString:String?,dateEndString:String?, isPaidInt: Int, searchTermString: String?){
        //TODO finish search function link
        _navigateToMainFragment.postValue(false)
        try {
            //Avoid sending blank content and format error
            val loanerNameChecked : String? = if (loanerName.isNullOrEmpty()) null else loanerName
            val searchTermStringChecked : String? = if (searchTermString.isNullOrEmpty()) null else searchTermString
            val amountStartFloat: Float? =
                if (amountStartString.isNullOrEmpty()) null else amountStartString.toFloat()
            val amountEndFloat: Float? =
                if (amountEndString.isNullOrEmpty()) null else amountEndString.toFloat()
            val dateStartLong: Long? =
                if (dateStartString.isNullOrEmpty()) null else dateStartString.toLong()
            val dateEndLong: Long? =
                if (dateEndString.isNullOrEmpty()) null else dateEndString.toLong()
            val isPaidBoolean: Boolean? = when (isPaidInt) {
                0 -> null
                1 -> true
                2 -> false
                else -> null
            }

            viewModelScope.launch {
                loanRepository.loanSearch(
                    loanerNameChecked,
                    amountStartFloat,
                    amountEndFloat,
                    dateStartLong,
                    dateEndLong,
                    isPaidBoolean,
                    searchTermStringChecked
                )
            }
            _isInDefault.postValue(false)
            if (!loanerNameChecked.isNullOrEmpty()) {
                _nowTitle.postValue(loanerNameChecked!!)
            }
            _navigateToMainFragment.postValue(true)
        } catch (e:Exception){
            Log.d("Search Error","Search Error: $e")
        }
    }


    fun loanDelete(loanId:Int){
        _navigateToMainFragment.postValue(false)
        try {
            viewModelScope.launch {
                loanRepository.loanDelete(loanId)
            }
            _navigateToMainFragment.postValue(true)
        } catch (e:Exception){
            Log.d("Delete Error","Delete Error: $e")
        }


    }


    fun loanInsert(loanerName :String?, amountString :String?, dateString :String?, isPaidInt :Int, note:String?){
        _navigateToMainFragment.postValue(false)
        try {
            if (loanerName.isNullOrEmpty() || amountString.isNullOrEmpty() || dateString.isNullOrEmpty() || note.isNullOrEmpty()){
                _insertFieldsIsEmpty.postValue(true) //maybe can add witch field is blank message
                _navigateToMainFragment.postValue(false)
            } else {
                val isPaidBoolean = isPaidInt == 0
                val amountFloat = amountString.toFloat()
                val dateLong = SimpleDateFormat("yyyy/MM/dd").parse(dateString).time
                val loan2Insert = LoanEntity(
                    loanerName = loanerName,
                    amount = amountFloat,
                    date = dateLong,
                    isPaid = isPaidBoolean,
                    note = note
                )
                viewModelScope.launch {
                    loanRepository.loanInsert(loan2Insert)
                }
                _insertFieldsIsEmpty.postValue(false)
                _navigateToMainFragment.postValue(true)
            }
        } catch (e:Exception){
            Log.d("Add Error","Add Error: $e")
        }

    }
    fun loanUpdate(loanId:Int,loanerName :String, amountString :String, dateString :String, isPaidInt :Int, note:String){
        _navigateToMainFragment.postValue(false)
        try {
            if (loanerName.isEmpty() || amountString.isEmpty() || dateString.isEmpty() || note.isEmpty()){
                _insertFieldsIsEmpty.postValue(true) //maybe can add witch field is blank message
            } else {
                val isPaidBoolean = isPaidInt==0
                val amountFloat = amountString.toFloat()
                val dateLong = SimpleDateFormat("yyyy/MM/dd").parse(dateString).time
                val loan2Update = LoanEntity(loanId = loanId,loanerName = loanerName, amount = amountFloat, date = dateLong, isPaid = isPaidBoolean, note = note)
                viewModelScope.launch {
                    loanRepository.loanInsert(loan2Update)
                }
                _insertFieldsIsEmpty.postValue(false)
                _navigateToMainFragment.postValue(true)
            }
        } catch (e:Exception){
            Log.d("Update Error","Update Error: $e")
        }

    }

    //todo add function to clear selectedLoan

    fun loanSelected(loan : LoanEntity){
        selectedLoan.value = loan
    }

    fun searchEnd(){
        _isInDefault.postValue(true)
    }


    private fun searchResultInit(){
        viewModelScope.launch {
            loanRepository.loanSearch(null,null,null,null,null,null,null)
        }
    }
    private fun distinctLoanerNamesInit(){
        viewModelScope.launch {
            loanRepository.getDistinctLoanerNames()
        }
    }

}