package data.repository

import androidx.paging.PagingSource
import com.dak0ta.ricktionary.core.coroutine.CoroutineDispatchers
import com.dak0ta.ricktionary.core.network.data.api.service.CharactersService
import com.dak0ta.ricktionary.core.network.data.converter.CharacterGenderConverter
import com.dak0ta.ricktionary.core.network.data.converter.CharacterStatusConverter
import com.dak0ta.ricktionary.core.network.data.network.SafeApiCall
import com.dak0ta.ricktionary.core.network.data.repository.CharactersPagingSource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
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
class CharactersPagingSourceIntegrationTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: CharactersService
    private lateinit var scheduler: TestCoroutineScheduler
    private lateinit var dispatcher: TestDispatcher
    private lateinit var testDispatchers: CoroutineDispatchers
    private lateinit var safeApiCall: SafeApiCall

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
            override val main = dispatcher
            override val io = dispatcher
            override val default = dispatcher
            override val immediate = dispatcher
        }
        safeApiCall = SafeApiCall(testDispatchers)
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
    fun `GIVEN full valid first page WHEN load Refresh THEN returns LoadResult_Page`() = runTest(scheduler) {
        // Arrange
        val json = """
        {
          "info": { "count": 826, "pages": 42, "next": "https://rickandmortyapi.com/api/character?page=2", "prev": null },
          "results": [
            {
              "id": 1,
              "name": "Rick Sanchez",
              "status": "Alive",
              "species": "Human",
              "image": "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
            },
            {
              "id": 2,
              "name": "Morty Smith",
              "status": "Alive",
              "species": "Human",
              "image": "https://rickandmortyapi.com/api/character/avatar/2.jpeg"
            }
          ]
        }
        """.trimIndent()
        enqueueJson(json)

        val pagingSource = CharactersPagingSource(service, safeApiCall)

        // Act
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false,
            ),
        )
        scheduler.advanceUntilIdle()

        // Assert
        val request = mockWebServer.takeRequest()
        assertTrue(request.path!!.contains("page=1"))

        when (result) {
            is PagingSource.LoadResult.Page -> {
                assertEquals(2, result.data.size)
                assertEquals(1, result.data.first().id)
                assertEquals("Rick Sanchez", result.data.first().name)
                assertEquals("ALIVE", result.data.first().status.name)
                assertEquals(2, result.data[1].id)
                assertNull(result.prevKey)
                assertEquals(2, result.nextKey)
            }

            is PagingSource.LoadResult.Error -> fail("Expected Page but got Error: ${result.throwable}")
            is PagingSource.LoadResult.Invalid -> fail("Expected Page but got Invalid")
        }
    }

    @Test
    fun `GIVEN last empty page WHEN load Append THEN returns empty LoadResult_Page`() = runTest(scheduler) {
        // Arrange
        val jsonEmpty = """
        {
            "info": {
                "count": 826,
                "pages": 42,
                "next": null,
                "prev": "https://rickandmortyapi.com/api/character?page=41"
            },
            "results": []
        }
        """.trimIndent()
        enqueueJson(jsonEmpty)

        val pagingSource = CharactersPagingSource(service, safeApiCall)

        // Act
        val result = pagingSource.load(
            PagingSource.LoadParams.Append(
                key = 42,
                loadSize = 20,
                placeholdersEnabled = false,
            ),
        )
        scheduler.advanceUntilIdle()

        // Assert
        when (result) {
            is PagingSource.LoadResult.Page -> {
                assertTrue(result.data.isEmpty())
                assertEquals(41, result.prevKey)
                assertNull(result.nextKey)
            }

            is PagingSource.LoadResult.Error -> fail("Expected Page but got Error: ${result.throwable}")
            is PagingSource.LoadResult.Invalid -> fail("Expected Page but got Invalid")
        }
    }

    @Test
    fun `GIVEN server error WHEN load Refresh THEN returns LoadResult_Error`() = runTest(scheduler) {
        // Arrange
        val json = """
        {
            "error": "Internal Server Error"
        }
        """.trimIndent()
        enqueueJson(json, code = 500)

        val pagingSource = CharactersPagingSource(service, safeApiCall)

        // Act
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false,
            ),
        )

        // Assert
        when (result) {
            is PagingSource.LoadResult.Error -> assertNotNull(result.throwable)
            is PagingSource.LoadResult.Page -> fail("Expected Error but got Page")
            is PagingSource.LoadResult.Invalid -> fail("Expected Error but got Invalid")
        }
    }

    @Test
    fun `GIVEN malformed JSON WHEN load Refresh THEN returns LoadResult_Error`() = runTest(scheduler) {
        // Arrange
        val json = """
        {
            "info": {
                "count": 826,
                "pages": 42,
                "next": null,
                "prev": null
            },
            "results": [
                {
                    "id": "not_an_int"
                }
            ]
        }
        """.trimIndent()
        enqueueJson(json)

        val pagingSource = CharactersPagingSource(service, safeApiCall)

        // Act
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false,
            ),
        )

        // Assert
        when (result) {
            is PagingSource.LoadResult.Error -> assertTrue(result.throwable is Exception)
            is PagingSource.LoadResult.Page -> fail("Expected Error but got Page")
            is PagingSource.LoadResult.Invalid -> fail("Expected Error but got Invalid")
        }
    }
}
