package com.dak0ta.ricktionary.core.network.data.repository

import com.dak0ta.ricktionary.core.coroutine.CoroutineDispatchers
import com.dak0ta.ricktionary.core.network.data.api.service.CharactersService
import com.dak0ta.ricktionary.core.network.data.api.service.EpisodesService
import com.dak0ta.ricktionary.core.network.data.converter.CharacterGenderConverter
import com.dak0ta.ricktionary.core.network.data.converter.CharacterStatusConverter
import com.dak0ta.ricktionary.core.network.data.network.SafeApiCall
import com.dak0ta.ricktionary.core.network.domain.model.ApiResult
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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class CharactersRemoteRepositoryImplIntegrationTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var charactersService: CharactersService
    private lateinit var episodesService: EpisodesService
    private lateinit var safeApiCall: SafeApiCall
    private lateinit var testDispatchers: CoroutineDispatchers
    private lateinit var scheduler: TestCoroutineScheduler
    private lateinit var dispatcher: TestDispatcher
    private lateinit var repository: CharactersRemoteRepositoryImpl

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

        charactersService = retrofit.create(CharactersService::class.java)
        episodesService = retrofit.create(EpisodesService::class.java)

        scheduler = TestCoroutineScheduler()
        dispatcher = StandardTestDispatcher(scheduler)
        testDispatchers = object : CoroutineDispatchers {
            override val main: CoroutineDispatcher = dispatcher
            override val io: CoroutineDispatcher = dispatcher
            override val default: CoroutineDispatcher = dispatcher
            override val immediate: CoroutineDispatcher = dispatcher
        }
        safeApiCall = SafeApiCall(testDispatchers)

        repository = CharactersRemoteRepositoryImpl(charactersService, episodesService, safeApiCall)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private fun enqueueJson(body: String, code: Int = 200) {
        val response = MockResponse()
            .setResponseCode(code)
            .setBody(body)
            .addHeader("Content-Type", "application/json; charset=utf-8")
        mockWebServer.enqueue(response)
    }

    @Test
    fun `GIVEN valid character id WHEN getCharacterById THEN returns CharacterDetail with episodes`() =
        runTest(scheduler) {
            // Arrange
            val characterJson = """
            {
                "id": 1,
                "name": "Rick Sanchez",
                "status": "Alive",
                "species": "Human",
                "type": "",
                "gender": "Male",
                "origin": {
                    "name": "Earth (C-137)",
                    "url": ""
                },
                "location": {
                    "name": "Citadel of Ricks",
                    "url": ""
                },
                "image": "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                "episode": [
                    "https://rickandmortyapi.com/api/episode/1",
                    "https://rickandmortyapi.com/api/episode/2"
                ]
            }
            """.trimIndent()

            val episodesJson = """
            [
                { "id": 1, "name": "Pilot", "episode": "S01E01" },
                { "id": 2, "name": "Lawnmower Dog", "episode": "S01E02" }
            ]
            """.trimIndent()

            enqueueJson(characterJson)
            enqueueJson(episodesJson)

            // Act
            val result = repository.getCharacterById(1)
            scheduler.advanceUntilIdle()

            // Assert
            when (result) {
                is ApiResult.Success -> {
                    val character = result.data
                    assertEquals(1, character.id)
                    assertEquals("Rick Sanchez", character.name)
                    assertEquals(2, character.episodes.size)
                    assertEquals("Pilot", character.episodes[0].name)
                }

                is ApiResult.Failure -> fail("Expected success but got failure: ${result.error}")
            }

            val request1 = mockWebServer.takeRequest()
            val request2 = mockWebServer.takeRequest()
            assertTrue(request1.path!!.contains("/character/1"))
            assertTrue(request2.path!!.contains("/episode/1,2"))
        }

    @Test
    fun `GIVEN character id not found WHEN getCharacterById THEN returns ApiResult_Failure`() = runTest(scheduler) {
        // Arrange
        val json = """
        {
            "error": "Not Found"
        }
        """.trimIndent()
        enqueueJson(json, code = 404)

        // Act
        val result = repository.getCharacterById(999)
        scheduler.advanceUntilIdle()

        // Assert
        when (result) {
            is ApiResult.Success -> fail("Expected failure but got success")
            is ApiResult.Failure -> assertNotNull(result.error)
        }

        val request = mockWebServer.takeRequest()
        assertTrue(request.path!!.contains("/character/999"))
    }

    @Test
    fun `GIVEN server error WHEN getCharacterById THEN returns ApiResult_Failure`() = runTest(scheduler) {
        // Arrange
        val json = """
        {
            "error": "Internal Server Error"
        }
        """.trimIndent()
        enqueueJson(json, code = 500)

        // Act
        val result = repository.getCharacterById(1)
        scheduler.advanceUntilIdle()

        // Assert
        when (result) {
            is ApiResult.Success -> fail("Expected failure but got success")
            is ApiResult.Failure -> assertNotNull(result.error)
        }

        val request = mockWebServer.takeRequest()
        assertTrue(request.path!!.contains("/character/1"))
    }

    @Test
    fun `GIVEN malformed episode JSON WHEN getCharacterById THEN returns ApiResult_Failure`() = runTest(scheduler) {
        // Arrange
        val characterJson = """
        {
            "id": 1,
            "name": "Rick Sanchez",
            "status": "Alive",
            "species": "Human",
            "type": "",
            "gender": "Male",
            "origin": {
                "name": "Earth (C-137)",
                "url": ""
            },
            "location": {
                "name": "Citadel of Ricks",
                "url": ""
            },
            "image": "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            "episode": [
                "https://rickandmortyapi.com/api/episode/1"
            ]
        }
        """.trimIndent()

        val malformedEpisodeJson = """
        {
            "id": "not_an_int",
            "name": "Pilot",
            "episode": "S01E01"
        }
        """.trimIndent()

        enqueueJson(characterJson)
        enqueueJson(malformedEpisodeJson)

        // Act
        val result = repository.getCharacterById(1)
        scheduler.advanceUntilIdle()

        // Assert
        when (result) {
            is ApiResult.Success -> fail("Expected failure but got success")
            is ApiResult.Failure -> assertNotNull(result.error)
        }
    }
}
