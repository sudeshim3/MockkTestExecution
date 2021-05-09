package com.example.simpletestexecution

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import com.example.simpletestexecution.helper.Result
import javax.inject.Inject

class NumberViewModel @Inject constructor(private val appRepository: AppRepository) :
    ViewModel() {

    private val numberFetchLiveData: MutableLiveData<FetchState> = MutableLiveData()

    val liveData
        get() = numberFetchLiveData as LiveData<FetchState>

    fun getRandomNumber() {
        viewModelScope.launch {
            appRepository.getRandomNumber().onStart {
                numberFetchLiveData.value = FetchState.Loading
            }.collect { response ->
                when(response) {
                    is Result.Success -> numberFetchLiveData.value = FetchState.Data(response.data[0].asInt)
                    is Result.Error -> numberFetchLiveData.value = FetchState.Error
                }
            }
        }

    }

    private fun mapError(error: Throwable) {

    }
}