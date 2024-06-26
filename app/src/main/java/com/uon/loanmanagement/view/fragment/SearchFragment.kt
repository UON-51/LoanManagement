package com.uon.loanmanagement.view.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.uon.loanmanagement.R
import com.uon.loanmanagement.databinding.FragmentSearchBinding
import com.uon.loanmanagement.viewmodel.LoanViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    val loanViewModel: LoanViewModel by activityViewModels()
    private val calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false)

        binding.viewModel = loanViewModel
        binding.lifecycleOwner = requireActivity()



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchDateEndTextView.setOnClickListener{
            showDatePickerDialog(binding.searchDateEndTextView)
        }
        binding.searchDateStartTextView.setOnClickListener{
            showDatePickerDialog(binding.searchDateStartTextView)
        }
        loanViewModel.distinctLoanerName.observe(viewLifecycleOwner){
            val adapter = ArrayAdapter<String>(
                binding.searchLoanerNameAutoCompleteTextView.context,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                it
            )
            binding.searchLoanerNameAutoCompleteTextView.setAdapter(adapter)
        }

    }

    private fun showDatePickerDialog(item : TextView) {

        val datePickerDialog = DatePickerDialog(requireContext(),{
                DatePicker,
                year:Int,
                monthOfYear:Int,
                dayOfMonth:Int ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year,monthOfYear,dayOfMonth)
            val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate.timeInMillis)
            item.text = formattedDate

        },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)

        )
        datePickerDialog.show()

    }
}