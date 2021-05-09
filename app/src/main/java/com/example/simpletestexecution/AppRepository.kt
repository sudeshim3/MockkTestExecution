package com.example.simpletestexecution

import com.example.simpletestexecution.helper.Result
import com.google.gson.JsonArray
import kotlinx.coroutines.flow.Flow
import org.json.JSONArray

interface AppRepository {
     fun getRandomNumber(): Flow<Result<JsonArray>>
}