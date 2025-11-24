package com.dak0ta.ricktionary.core.network.data.network

import com.dak0ta.ricktionary.core.coroutine.CoroutineDispatchers
import com.dak0ta.ricktionary.core.network.data.api.service.CharactersService
import com.dak0ta.ricktionary.core.network.data.converter.CharacterGenderConverter
import com.dak0ta.ricktionary.core.network.data.converter.CharacterStatusConverter
import com.dak0ta.ricktionary.core.network.domain.model.ApiResult
import com.dak0ta.ricktionary.core.network.domain.model.NetworkError
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class SafeApiCallIntegrationTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: CharactersService
    private lateinit var safeApiCall: SafeApiCall
    private lateinit var testDispatchers: CoroutineDispatchers
    private lateinit var scheduler: TestCoroutineScheduler
    private lateinit var dispatcher: TestDispatcher

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val moshi = Moshi.Builder()
            .add(CharacterGenderConverter())
            .add(CharacterStatusConverter())
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        service = retrofit.create(CharactersService::class.java)

        scheduler = TestCoroutineScheduler()
        dispatcher = StandardTestDispatcher(scheduler)

        testDispatchers = object : CoroutineDispatchers {
            override val main: CoroutineDispatcher = dispatcher
            override val io: CoroutineDispatcher = dispatcher
            override val default: CoroutineDispatcher = dispatcher
            override val immediate: CoroutineDispatcher = dispatcher
        }

        safeApiCall = SafeApiCall(testDispatchers)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private fun enqueueJson(body: String, code: Int = 200) {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(body)
                .addHeader("Content-Type", "application/json; charset=utf-8"),
        )
    }

    @Test
    fun `GIVEN valid character WHEN call SafeApiCall THEN returns ApiResult_Success`() = runTest(scheduler) {
        // Arrange
        val json = """
        {
            "id": 1,
            "name": "Rick Sanchez",
            "status": "Alive",
            "species": "Human",
            "type": "",
            "gender": "Male",
            "origin": {
                "name": "Earth (C-137)",
                "url": "https://rickandmortyapi.com/api/location/1"
            },
            "location": {
                "name": "Citadel of Ricks",
                "url": "https://rickandmortyapi.com/api/location/3"
            },
            "image": "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            "episode": [],
            "url": "https://rickandmortyapi.com/api/character/1",
            "created": "2017-11-04T18:48:46.250Z"
        }
        """.trimIndent()
        enqueueJson(json)

        // Act
        val result = safeApiCall("GET_CHARACTER") { service.getCharacterById(1) }
        scheduler.advanceUntilIdle()

        // Assert
        when (result) {
            is ApiResult.Success -> Assert.assertEquals(1, result.data.id)
            is ApiResult.Failure -> Assert.fail("Expected Success but got Failure: ${result.error}")
        }

        val request = mockWebServer.takeRequest()
        Assert.assertEquals("/character/1", request.path)
    }

    @Test
    fun `GIVEN server error WHEN call SafeApiCall THEN returns ApiResult_Failure_Http`() = runTest(scheduler) {
        // Arrange
        val json = """
        {
            "error": "Internal Server Error"
        }
        """.trimIndent()
        enqueueJson(json, code = 500)

        // Act
        val result = safeApiCall("GET_CHARACTER") { service.getCharacterById(1) }
        scheduler.advanceUntilIdle()

        // Assert
        when (result) {
            is ApiResult.Failure -> Assert.assertTrue(result.error is NetworkError.Http)
            is ApiResult.Success -> Assert.fail("Expected Failure but got Success")
        }

        val request = mockWebServer.takeRequest()
        Assert.assertEquals("/character/1", request.path)
    }

    @Test
    fun `GIVEN malformed JSON WHEN call SafeApiCall THEN returns ApiResult_Failure_Parse`() = runTest(scheduler) {
        // Arrange
        val json = """
        {
            "id": "not_an_int"
        }
        """.trimIndent()
        enqueueJson(json)

        // Act
        val result = safeApiCall("GET_CHARACTER") { service.getCharacterById(1) }
        scheduler.advanceUntilIdle()

        // Assert
        when (result) {
            is ApiResult.Failure -> Assert.assertTrue(result.error is NetworkError.Parse)
            is ApiResult.Success -> Assert.fail("Expected Failure but got Success")
        }
    }
}
