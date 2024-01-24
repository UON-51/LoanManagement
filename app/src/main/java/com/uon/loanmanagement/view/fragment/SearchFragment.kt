package com.uon.loanmanagement.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.uon.loanmanagement.R
import com.uon.loanmanagement.databinding.FragmentMainBinding
import com.uon.loanmanagement.databinding.FragmentSearchBinding
import com.uon.loanmanagement.viewmodel.LoanViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    val loanViewModel: LoanViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false)

        binding.viewModel = loanViewModel
        binding.lifecycleOwner = requireActivity()



        return binding.root
    }

}