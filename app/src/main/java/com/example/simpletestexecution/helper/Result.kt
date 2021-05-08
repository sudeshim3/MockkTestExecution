package com.example.simpletestexecution.helper

sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()

    data class Error(val errorCode: Int, val error: Throwable) : Result<Nothing>()
}
