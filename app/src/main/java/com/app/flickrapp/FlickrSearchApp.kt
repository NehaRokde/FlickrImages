package com.app.flickrapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * [FlickrSearchApp] is the main application class for the Flickr search app.
 * This class creation of the Hilt component for dependency injection and the generation of a
 * Hilt component that is available throughout the application’s lifecycle.
 */
@HiltAndroidApp
class FlickrSearchApp : Application()