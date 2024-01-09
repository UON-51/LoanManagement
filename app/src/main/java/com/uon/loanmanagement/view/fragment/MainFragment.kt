package com.uon.loanmanagement.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.uon.loanmanagement.R
import com.uon.loanmanagement.databinding.FragmentMainBinding
import com.uon.loanmanagement.view.recycleview.LoanAdapter
import com.uon.loanmanagement.viewmodel.LoanViewModel

class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: LoanViewModel
    private lateinit var recyclerViewAdapter: LoanAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
        viewModel = ViewModelProvider(requireActivity()).get(LoanViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = requireActivity()
        recyclerViewAdapter = LoanAdapter(viewModel)
        binding.recyclerView.adapter = recyclerViewAdapter

        //viewModel.loanInsertTest()



        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration : DividerItemDecoration = DividerItemDecoration(binding.recyclerView.context,layoutManager.orientation)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        viewModel.allLoans.observe(viewLifecycleOwner, Observer { loans ->
            Log.d("RoomTest",loans.toString())
            recyclerViewAdapter.setItems(loans)
        })


        return binding.root
    }



}