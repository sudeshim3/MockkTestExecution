package com.example.simpletestexecution.datasource

import com.example.simpletestexecution.helper.Result
import com.google.gson.JsonArray
import org.json.JSONArray

interface RemoteDataSource {

    suspend fun getRandomNumber(): Result<JsonArray>
}