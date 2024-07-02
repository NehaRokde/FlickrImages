package com.app.flickrapp.di

import com.app.flickrapp.api.FlickrApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
/**
 * [NetworkModule] provides network-related dependencies for the application.
 * This module is used to set up Retrofit and OkHttpClient for network communication with the Flickr API.
 *
 * This class is annotated with [@Module] and [@InstallIn(SingletonComponent::class)] to indicate that it
 * provides dependencies that are available throughout the application’s lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides a [Retrofit] instance for making network requests.
     * @return [Retrofit] The Retrofit instance for network operations.
     */
    @Provides
    @Singleton
    fun provideRetrofit() :Retrofit{

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.addNetworkInterceptor(logging)
        val okHttpClient = httpClient.build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    /**
     * Provides an instance of [FlickrApi] for interacting with the Flickr public photo feed API.
     * @return [FlickrApi] The API service interface for making requests to the Flickr API.
     */
    @Provides
    @Singleton
    fun provideFlickrApi(retrofit: Retrofit): FlickrApi {
        return retrofit.create(FlickrApi::class.java)
    }
}

object Constants {
    /**
     * The base URL for the Flickr API endpoints.
     */
    const val BASE_URL = "https://api.flickr.com/services/"
}