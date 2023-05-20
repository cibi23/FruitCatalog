package hu.bme.aut.android.fruitcatalog.network

import hu.bme.aut.android.fruitcatalog.model.Fruit
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.nio.charset.StandardCharsets


@RunWith(JUnit4::class)
abstract class ApiTest<T> {

    lateinit var mockWebServer: MockWebServer

    @Before
    fun mockServer() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun stopServer() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetById() {
        val response = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setBody(Fruit.mock().toString())

        mockWebServer.enqueue(response)
    }

    @Test
    fun testGetAllFruits(){
        val response = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .setBody(listOf(Fruit.mock()).toString())

        mockWebServer.enqueue(response)
    }
}