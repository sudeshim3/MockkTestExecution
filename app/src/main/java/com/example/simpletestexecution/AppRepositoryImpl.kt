package com.example.simpletestexecution

import com.example.simpletestexecution.datasource.RemoteDataSource
import com.example.simpletestexecution.dispatcher.DispatcherProvider
import com.example.simpletestexecution.helper.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONArray
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val dispatcher: DispatcherProvider
): AppRepository {

    override fun getRandomNumber(): Flow<Result<JSONArray>> {
        return flow {
            emit(remoteDataSource.getRandomNumber())
        }.flowOn(dispatcher.io)
    }
}