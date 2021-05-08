package com.example.simpletestexecution

import com.example.simpletestexecution.helper.Result
import retrofit2.Response

suspend fun <T : Any> safeApiRequest(
    call: suspend () -> Response<T>
): Result<T> {
    return try {
        val response = call()
        when {
            response.isSuccessful && (response.body() is T) -> {
                Result.Success(response.body()!!)
            }
            else -> {
                Result.Error(response.code(), Exception(response.errorBody()?.charStream()?.readText()))
            }
        }
    } catch (exception: Exception) {
        Result.Error(0 ,exception)
    }
}
