package com.example.simpletestexecution.datasource

import com.example.simpletestexecution.helper.Result
import com.example.simpletestexecution.api.NumberApi
import com.example.simpletestexecution.safeApiRequest
import org.json.JSONArray
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val numberApi: NumberApi) :
    RemoteDataSource {

    override suspend fun getRandomNumber(): Result<JSONArray> =
        safeApiRequest { numberApi.getRandomNumber() }
}