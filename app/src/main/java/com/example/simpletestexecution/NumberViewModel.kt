package com.example.simpletestexecution

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class NumberViewModel @Inject constructor(private val repositoryImpl: AppRepositoryImpl): ViewModel() {

    sealed class Sta

    fun getRandomNumber() {
        viewModelScope.launch {
            repositoryImpl.getRandomNumber().onStart {

            }
        }

    }
}