package com.example.simpletestexecution

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.simpletestexecution.api.NumberApi
import com.example.simpletestexecution.datasource.RemoteDataSource
import com.example.simpletestexecution.datasource.RemoteDataSourceImpl
import com.example.simpletestexecution.helper.Result
import com.example.simpletestexecution.utils.TestDispatcherProviderImpl
import com.google.common.truth.Truth
import com.google.gson.JsonArray
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
@ExperimentalTime
class AppRepositoryImplTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    lateinit var remoteDataSource: RemoteDataSource

    @MockK
    lateinit var numberApi: NumberApi

    lateinit var dispatcher: TestCoroutineDispatcher

    private lateinit var repository: AppRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        remoteDataSource = RemoteDataSourceImpl(numberApi)
        dispatcher = TestCoroutineDispatcher()
        repository = AppRepositoryImpl(remoteDataSource, TestDispatcherProviderImpl(dispatcher))
    }

    @Test
    fun `get random number give number`() = dispatcher.runBlockingTest {
        coEvery { numberApi.getRandomNumber() } returns Response.success(JsonArray())
        val result = repository.getRandomNumber().toList()
        Truth.assertThat(result.size).isEqualTo(1)
        Truth.assertThat(result.first()).isInstanceOf(Result.Success::class.java)
    }

    @Test
    fun `repository gives error response on API failure`() = dispatcher.runBlockingTest {
        coEvery { numberApi.getRandomNumber() } returns Response.error(
            401,
            ResponseBody.create(null, "Unauthorized access Exception")
        )
        val result = repository.getRandomNumber().toList()
        Truth.assertThat(result.size).isEqualTo(1)
        Truth.assertThat(result.first()).isInstanceOf(Result.Error::class.java)
    }
}