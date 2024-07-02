package com.app.flickrapp.api

import com.app.flickrapp.Helper
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FlickrApiTest {

    private lateinit var mockWebServer: MockWebServer
    lateinit var flickrApi: FlickrApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        flickrApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(FlickrApi::class.java)
    }

    @Test
    fun testSearchPhotos_returnFlickrResponse() = runTest {
        val tags = "porcupine"

        val mockResponse = MockResponse()
        val content = Helper.readFileResource("/response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val response = flickrApi.searchPhotos(tags = tags)
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.body().toString().isEmpty())
        Assert.assertEquals("Orange Choppers", response.body()!!.items[0].title)
    }

    @Test
    fun testSearchPhotos_returnError() = runTest {
        val tags = "porcupine"

        val mockResponse = MockResponse()
        mockResponse.setResponseCode(404)
        mockResponse.setBody("Search Not Found")
        mockWebServer.enqueue(mockResponse)

        val response = flickrApi.searchPhotos(tags = tags)
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.isSuccessful)
        Assert.assertEquals(404, response.code())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}