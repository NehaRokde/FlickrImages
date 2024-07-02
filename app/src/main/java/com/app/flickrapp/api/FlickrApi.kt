package com.app.flickrapp.api

import com.app.flickrapp.data.model.FlickrResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * [FlickrApi] interface defines the API methods to interact with the Flickr public photo feeds.
 * This API provides a way to fetch public photos based on tags using the Flickr API.
 *
 * The base URL for the Flickr public photo feed API is:
 *  https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1
 */

interface FlickrApi {

    /**
     * This method performs a network request to the Flickr public photo feed endpoint to retrieve
     * photos based on the provided tags.
     *
     * @param format The format of the response. Defaults to "json".
     * @param nojsoncallback A flag to prevent the JSONP callback wrapping of the response. Defaults to 1
     * @param tags A comma-separated list of tags to filter the photos by.
     *             Example: `forest,bird`
     *
     * @return [Response<FlickrResponse>] A [Response] object containing the [FlickrResponse] with the photo data or an error.
     *
     * @throws [IOException] If there is an error making the network request.
     * @throws [HttpException] If the HTTP request fails (e.g., 404 or 500 errors).
     */

    @GET("feeds/photos_public.gne")
    suspend fun searchPhotos(
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: Int = 1,
        @Query("tags") tags: String
    ): Response<FlickrResponse>

}