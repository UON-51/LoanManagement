package com.uon.loanmanagement.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment


import com.uon.loanmanagement.R
import com.uon.loanmanagement.view.fragment.SearchFragment
import com.uon.loanmanagement.databinding.ActivityMainBinding
import com.uon.loanmanagement.view.fragment.AddFragment

import com.uon.loanmanagement.view.fragment.EditFragment
import com.uon.loanmanagement.view.fragment.MainFragment
import com.uon.loanmanagement.viewmodel.LoanViewModel



class MainActivity : AppCompatActivity() {
    private val loanViewModel: LoanViewModel by viewModels()
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToFragment(MainFragment())

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.viewModel=loanViewModel
        binding.lifecycleOwner = this
        binding.topToolBar.navigationIcon = null

        binding.topToolBar.setOnMenuItemClickListener{menuItem ->
            when(menuItem.itemId){
                R.id.menu_search -> {
                    navigateToFragment(SearchFragment())
                    showNavigationIcon()
                    binding.topToolBar.setTitle(R.string.search_record)
                    true
                }
                R.id.menu_add -> {
                    navigateToFragment(AddFragment())
                    showNavigationIcon()
                    binding.topToolBar.setTitle(R.string.add_record)
                    true
                }
                else -> {false}
            }
        }

        binding.topToolBar.setNavigationOnClickListener {
            navigateToFragment(MainFragment())
            binding.topToolBar.setTitle(R.string.all_record)
        }

        loanViewModel.selectedLoan.observe(this){
            navigateToFragment(EditFragment())
            showNavigationIcon()
            binding.topToolBar.setTitle(R.string.edit_record)
        }

        loanViewModel.isInDefault.observe(this){
            if (it){
                binding.topToolBar.navigationIcon = null
            } else {
                showNavigationIcon()
            }
        }
        loanViewModel.navigateToMainFragment.observe(this){
            if (it){
                navigateToFragment(MainFragment())
            }
        }
    }

    private fun showNavigationIcon(){
        binding.topToolBar.setNavigationIcon(com.google.android.material.R.drawable.material_ic_keyboard_arrow_left_black_24dp)
    }

    private fun navigateToFragment(targetFragment:Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView,targetFragment)
            .commit()
    }
}