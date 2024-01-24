package com.uon.loanmanagement.view.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.uon.loanmanagement.R
import com.uon.loanmanagement.databinding.FragmentEditAddBinding
import com.uon.loanmanagement.viewmodel.LoanViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

//todo make it conform to the MVVM design pattern
//todo navigation button set selectedLoan to null
class EditAddFragment : Fragment() {
    private lateinit var binding: FragmentEditAddBinding
    val loanViewModel: LoanViewModel by activityViewModels()
    var loanId : String? = null
    private val calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_edit_add,container,false)

        binding.viewModel = loanViewModel
        binding.lifecycleOwner = requireActivity()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dateEditText.setOnClickListener{
            showDatePickerDialog()
        }

        loanViewModel.selectedLoan.observe(requireActivity()){
            if (it != null){
                binding.buttonEditAddConfirm.setText(R.string.edit)
                loanId = it.loanId.toString()
                binding.loanerNameAutoCompleteTextView.setText(it.loanerName)
                binding.amountEditText.setText(it.amount.toString())
                binding.dateEditText.setText(SimpleDateFormat("yyyy/MM/dd").format(Date(it.date)))
                binding.noteEditText.setText(it.note)
                if (it.isPaid){
                    binding.isPaidSpinner.setSelection(0)
                } else {
                    binding.isPaidSpinner.setSelection(1)
                }
            }
        }

        binding.buttonEditAddConfirm.setOnClickListener{
            val loanerName = binding.loanerNameAutoCompleteTextView.text.toString()
            val loanAmount = binding.amountEditText.text.toString().toFloat()
            val loanDate = SimpleDateFormat("yyyy/MM/dd").parse(binding.dateEditText.text.toString()).time
            val loanIsPaid = binding.isPaidSpinner.selectedItem==0
            val loanNote = binding.noteEditText.text.toString()

            Log.d(
                "addTest",
                "loanerName = $loanerName," +
                        "loanAmount = $loanAmount," +
                        "loanDate = $loanDate," +
                        "loanIsPaid = $loanIsPaid," +
                        "loanNote = $loanNote")



            loanViewModel.loanInsert(loanId,loanerName,loanAmount,loanDate,loanIsPaid,loanNote)
        }


    }

    private fun showDatePickerDialog() {

        val datePickerDialog = DatePickerDialog(requireContext(),{
                DatePicker,
                year:Int,
                monthOfYear:Int,
                dayOfMonth:Int ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year,monthOfYear,dayOfMonth)
            val dateFormat = SimpleDateFormat("YYYY/MM/DD",Locale.getDefault())
            val formatedDate = dateFormat.format(selectedDate.timeInMillis)
            binding.dateEditText.setText(formatedDate)

        },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),

            )
        datePickerDialog.show()

    }


}