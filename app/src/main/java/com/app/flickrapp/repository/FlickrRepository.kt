package com.app.flickrapp.repository

import com.app.flickrapp.api.FlickrApi
import com.app.flickrapp.data.model.FlickrResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
/**
 * [FlickrRepository] handles data operations related to Flickr public photos.
 * It provides methods to interact with the [FlickrApi] to fetch photos based on tags.
 *
 * This class is annotated with [@Singleton] to ensure that only one instance of the repository
 * is created throughout the application's lifecycle, promoting efficient resource management.
 */
@Singleton
class FlickrRepository @Inject constructor(private val flickrApi: FlickrApi) {

    /**
     * Fetches public photos from Flickr based on the provided tags.
     * This method calls the [searchPhotos] method on the [FlickrApi] interface to retrieve photos.
     * If the request is successful, it returns the [FlickrResponse] containing the photos.
     * If the request fails, it throws an exception.
     * @param tags A comma-separated list of tags to filter the photos by.
     *             Example: `"sunset,beach"`
     * @return [FlickrResponse] The response containing the list of photos.
     * @throws [Exception] If the API call is unsuccessful or there is an error.
     */
    suspend fun searchPhotos(tags: String): FlickrResponse {

        val response = flickrApi.searchPhotos(tags = tags)
        if (response.isSuccessful) {
            response.body()?.let {
                return it
            }
        }
        throw Exception("Failed to load Images")
    }
}