package com.uon.loanmanagement.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.uon.loanmanagement.R
import com.uon.loanmanagement.databinding.FragmentMainBinding
import com.uon.loanmanagement.view.recycleview.LoanAdapter
import com.uon.loanmanagement.viewmodel.LoanViewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    val loanViewModel: LoanViewModel by activityViewModels()

    private lateinit var recyclerViewAdapter: LoanAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
        binding.viewModel = loanViewModel
        binding.lifecycleOwner = requireActivity()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewAdapter = LoanAdapter(loanViewModel)
        binding.recyclerView.adapter = recyclerViewAdapter
        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(binding.recyclerView.context,layoutManager.orientation)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        loanViewModel.searchResult.observe(viewLifecycleOwner) { loans ->
            recyclerViewAdapter.setItems(loans)
        }

    }
}