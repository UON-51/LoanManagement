package com.uon.loanmanagement.view.adapter

import android.util.Log
import android.view.ViewTreeObserver
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.Date


@BindingAdapter("convertLongToDate")
fun convertLongToDate(textView: TextView, date:Long){
    textView.text = SimpleDateFormat("yyyy/MM/dd").format(Date(date))
}
@BindingAdapter("editLoanerNameInit")
fun editLoanerNameInit(autoCompleteTextView: AutoCompleteTextView, selectedLoanerName:String){
    autoCompleteTextView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            autoCompleteTextView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            autoCompleteTextView.setText(selectedLoanerName)
        }
    })
}


@BindingAdapter("editAmountInit")
fun editAmountInit(editText: EditText, selectedAmount:Float){
    editText.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener{
        override fun onGlobalLayout() {
            editText.viewTreeObserver.removeOnGlobalLayoutListener(this)
            editText.setText(selectedAmount.toString())
        }
    })
}
@BindingAdapter("editDateInit")
fun editDateInit(textView: TextView, selectedDate:Long){
    textView.viewTreeObserver.addOnGlobalLayoutListener(object :ViewTreeObserver.OnGlobalLayoutListener{
        override fun onGlobalLayout() {
            textView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            textView.text = SimpleDateFormat("yyyy/MM/dd").format(Date(selectedDate))
        }
    })
    Log.d("EditTest","selectedDate = $selectedDate")
}
@BindingAdapter("editIsPaidInit")
fun editIsPaidInit(spinner: Spinner, selectedIsPaid:Boolean){
    spinner.viewTreeObserver.addOnGlobalLayoutListener(object :ViewTreeObserver.OnGlobalLayoutListener{
        override fun onGlobalLayout() {
            spinner.viewTreeObserver.removeOnGlobalLayoutListener(this)
            spinner.setSelection(if (selectedIsPaid) 0 else 1)
        }
    })
}
@BindingAdapter("editNoteInit")
fun editNoteInit(editText: EditText, selectedNote:String){
    editText.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener{
        override fun onGlobalLayout() {
            editText.viewTreeObserver.removeOnGlobalLayoutListener(this)
            editText.setText(selectedNote)
        }
    })
}

