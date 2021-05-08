package com.example.simpletestexecution

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase
import org.junit.Rule

class NumberViewModelTest : TestCase() {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()



}