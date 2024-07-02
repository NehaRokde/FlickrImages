package com.app.flickrapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.app.flickrapp.ui.navigation.FlickrAppNavigation
import com.app.flickrapp.ui.theme.FlickrAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickrAppTheme {
                // A surface container using the 'background' color from the theme
                FlickrAppNavigation()
            }
        }
    }
}
