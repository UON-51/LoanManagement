package com.uon.loanmanagement


import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.uon.loanmanagement.view.MainActivity
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class UITest {

    @get:Rule
    val rule :ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)


}