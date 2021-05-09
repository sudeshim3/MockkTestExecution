package com.example.simpletestexecution

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class MovieVMFactory @Inject constructor(private val appRepository: AppRepositoryImpl) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NumberViewModel(appRepository) as T
    }
}
