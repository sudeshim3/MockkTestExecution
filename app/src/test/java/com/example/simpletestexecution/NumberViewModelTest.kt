package com.example.simpletestexecution

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import com.example.simpletestexecution.helper.Result
import com.google.common.truth.Truth.assertThat
import com.google.gson.JsonArray
import io.mockk.MockKAnnotations
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.*
import java.lang.Exception

@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NumberViewModelTest : CoroutineTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository: AppRepositoryImpl = mockk()

    override lateinit var testScope: TestCoroutineScope

    override lateinit var dispatcher: TestCoroutineDispatcher

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        dispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `fetching numbers should call livedata - Success`() = dispatcher.runBlockingTest{
        every { repository.getRandomNumber() } returns flow {
            emit(Result.Success(JsonArray().apply { add(42) }))
        }
        val viewModel = NumberViewModel(repository)
        viewModel.liveData.observerForTesting {
            viewModel.getRandomNumber()
            assertThat(it.values).hasSize(2)
            assertThat(it.values[0]).isInstanceOf(FetchState.Loading::class.java)
            assertThat(it.values[1]).isInstanceOf(FetchState.Data::class.java)
            assertThat((it.values[1] as FetchState.Data).number).isEqualTo(42)
        }
    }

    @Test
    fun `fetching number should call livedata - Failure`() = dispatcher.runBlockingTest {
        every { repository.getRandomNumber() } returns flow {
            emit(Result.Error(401, Exception()))
        }
        val viewModel = NumberViewModel(repository)
        viewModel.liveData.observerForTesting {
            viewModel.getRandomNumber()
            assertThat(it.values.size).isEqualTo(2)
            assertThat(it.values[0]).isInstanceOf(FetchState.Loading::class.java)
            assertThat(it.values[1]).isInstanceOf(FetchState.Error::class.java)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}

fun <T> LiveData<T>.observerForTesting(block: (ValueObserver<T>) -> Unit) {
    val observer = ValueObserver<T>()
    try {
        observeForever(observer)
        block(observer)
    } finally {
        removeObserver(observer)
    }
}

class ValueObserver<T>: Observer<T> {
    val values = mutableListOf<T>()

    override fun onChanged(t: T) {
        values.add(t)
    }
}

@ExperimentalCoroutinesApi
@ExtendWith(TestCoroutineExtension::class)
interface CoroutineTest {

    var testScope: TestCoroutineScope
    var dispatcher: TestCoroutineDispatcher

    @After
    fun after() {
        dispatcher.cleanupTestCoroutines()
    }
}

@ExperimentalCoroutinesApi
class TestCoroutineExtension: TestInstancePostProcessor, BeforeAllCallback, AfterEachCallback, AfterAllCallback {

    private val dispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(dispatcher)

    override fun postProcessTestInstance(testInstance: Any?, context: ExtensionContext?) {
        testInstance as CoroutineTest
        testInstance.testScope = testScope
        testInstance.dispatcher = dispatcher
    }

    override fun beforeAll(context: ExtensionContext?) {
        Dispatchers.setMain(dispatcher)
    }

    override fun afterEach(context: ExtensionContext?) {
        testScope.cleanupTestCoroutines()
    }

    override fun afterAll(context: ExtensionContext?) {
        Dispatchers.resetMain()
    }

}