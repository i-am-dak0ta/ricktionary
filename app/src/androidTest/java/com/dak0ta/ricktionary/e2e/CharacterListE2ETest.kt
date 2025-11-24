package com.dak0ta.ricktionary.e2e

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dak0ta.ricktionary.app.MainActivity
import com.dak0ta.ricktionary.feature.home.ui.test.TestTags
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
class CharacterListE2ETest {

    companion object {

        private lateinit var mockWebServer: MockWebServer

        @JvmStatic
        @BeforeClass
        fun setUpClass() {
            mockWebServer = MockWebServer()
            mockWebServer.start()
            System.setProperty("api.baseUrl", mockWebServer.url("/").toString())

            val listJson = CharacterListE2ETest::class.java.classLoader!!
                .getResourceAsStream("characters_page1.json")!!
                .bufferedReader()
                .use { it.readText() }

            mockWebServer.enqueue(MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(listJson))
        }

        @JvmStatic
        @AfterClass
        fun tearDownClass() {
            mockWebServer.shutdown()
        }
    }

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun whenAppLaunched_listIsDisplayed_andFirstItemExists() {
        composeRule.onRoot().printToLog("SEMANTICS_BEFORE_WAIT")

        composeRule.waitUntil(timeoutMillis = 5_000) {
            val nodes = composeRule.onAllNodesWithTag(TestTags.CHARACTER_LIST).fetchSemanticsNodes()
            nodes.isNotEmpty() && nodes.any { node ->
                val rect = node.boundsInRoot
                (rect.bottom - rect.top) > 0f
            }
        }

        composeRule.onNodeWithTag(TestTags.CHARACTER_LIST).assertIsDisplayed()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            val nodes = composeRule.onAllNodesWithTag(TestTags.characterItem(1)).fetchSemanticsNodes()
            nodes.isNotEmpty() && nodes.any { (it.boundsInRoot.bottom - it.boundsInRoot.top) > 0f }
        }

        composeRule.onNodeWithTag(TestTags.characterItem(1)).assertIsDisplayed()
    }
}
