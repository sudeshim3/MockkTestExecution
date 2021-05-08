package com.example.simpletestexecution.api

import org.json.JSONArray
import retrofit2.Response
import retrofit2.http.GET

interface NumberApi {

    @GET("random")
    suspend fun getRandomNumber(): Response<JSONArray>
}