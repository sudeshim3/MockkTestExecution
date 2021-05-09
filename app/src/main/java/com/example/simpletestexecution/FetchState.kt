package com.example.simpletestexecution

sealed class FetchState {
    object Loading: FetchState()
    object Error: FetchState()
    data class Data(val number: Int): FetchState()
}