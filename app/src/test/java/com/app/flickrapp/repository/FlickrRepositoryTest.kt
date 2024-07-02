package com.app.flickrapp.repository

import com.app.flickrapp.api.FlickrApi
import com.app.flickrapp.createSampleFlickrResponse
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class FlickrRepositoryTest {

    @Mock
    lateinit var flickrApi: FlickrApi

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `searchPhotos should return FlickrResponse when API call is successful`() = runBlocking {
        // Arrange
        // Arrange
        val tags = "Dog"
        val expectedFlickrResponse = createSampleFlickrResponse()
        Mockito.`when`(flickrApi.searchPhotos(tags = tags))
            .thenReturn(Response.success(expectedFlickrResponse))

        // Act
        val sut = FlickrRepository(flickrApi)
        val result = sut.searchPhotos(tags)

        // Assert
        assertEquals(expectedFlickrResponse, result)
    }

    @Test
    fun `searchPhotos should throw Exception when API call is not successful`() = runBlocking {
        // Arrange
        val tags = "Dog"
        Mockito.`when`(flickrApi.searchPhotos(tags = tags))
            .thenReturn(
                Response.error(
                    404,
                    "Not found".toResponseBody("text/plain".toMediaTypeOrNull())
                )
            )

        // Act & Assert
        val sut = FlickrRepository(flickrApi)
        val result = sut.searchPhotos(tags)

        Assert.assertEquals("Failed to load Images", result)
    }

}

