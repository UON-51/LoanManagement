package com.uon.loanmanagement.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData


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
    private val titleLiveData : MutableLiveData<String> = MutableLiveData()  //a liveData for observer when should navigationIcon display
    private lateinit var lastTitle : String  //variable for storage witch title should main_fragment display

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.viewModel=loanViewModel
        binding.lifecycleOwner = this
        binding.topToolBar.navigationIcon = null

        fragmentHandler(getString(R.string.all_record))  //initialize title and fragment to "all record" and main_fragment

        binding.topToolBar.setOnMenuItemClickListener{menuItem ->
            when(menuItem.itemId){
                R.id.menu_search -> {  //switch to search record fragment
                    fragmentHandler(getString(R.string.search_record))
                    true
                }
                R.id.menu_add -> {  //switch to add record fragment
                    fragmentHandler(getString(R.string.add_record))
                    true
                }
                else -> {false}
            }
        }

        binding.topToolBar.setNavigationOnClickListener {
            //because
            if (binding.topToolBar.title.toString() == getString(R.string.search_result)){
                loanViewModel.searchEnd()
            } else {
                fragmentHandler(lastTitle)
            }
        }

        //observe loanViewModel.selectedLoan and show the loan record witch the user clicked
        loanViewModel.selectedLoan.observe(this){
            fragmentHandler(getString(R.string.edit_record))
        }

        //set toolbar title base on loanViewModel.inSearching
        loanViewModel.inSearching.observe(this){
            lastTitle = if (it){
                getString(R.string.search_result)
            } else {
                getString(R.string.all_record)
            }
            fragmentHandler(lastTitle)

        }

        //observe loanViewModel.navigateToMainFragment to switch back to MainFragment
        loanViewModel.navigateToMainFragment.observe(this){
            if (it){
                fragmentHandler(lastTitle)
            }
        }


        titleLiveData.observe(this){
            when(it){
                getString(R.string.all_record) -> {
                    binding.topToolBar.navigationIcon = null
                }
                else -> {
                    binding.topToolBar.setNavigationIcon(
                        com.google.android.material.R.drawable.
                        material_ic_keyboard_arrow_left_black_24dp
                    )
                }

            }

        }
    }

    private fun navigateToFragment(targetFragment:Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView,targetFragment)
            .commit()
    }
     fun fragmentHandler(title2Switch:String){
        when (title2Switch){
            getString(R.string.edit_record) -> {
                navigateToFragment(EditFragment())

            }
            getString(R.string.add_record) -> {
                navigateToFragment(AddFragment())
            }
            getString(R.string.search_record) -> {
                navigateToFragment(SearchFragment())
            }
            else -> {
                navigateToFragment(MainFragment())
            }
        }
        binding.topToolBar.setTitle(title2Switch)
        titleLiveData.postValue(title2Switch)

    }

}