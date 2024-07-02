package com.app.flickrapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.flickrapp.data.model.FlickrItem
import com.app.flickrapp.ui.screens.PhotoDetails
import com.app.flickrapp.ui.screens.SearchScreen
import com.google.gson.Gson

@Composable
fun FlickrAppNavigation() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Flickr Images", fontSize = 20.sp) },
            )
        },
        content = { padding ->
            NavHost(
                navController = navController,
                startDestination = "search", Modifier.padding(padding)
            ) {
                composable("search") { SearchScreen(navController) }
                composable(
                    "details/{photoItem}",
                    arguments = listOf(navArgument("photoItem") { type = NavType.StringType })
                ) { backStackEntry ->
                    val photoItemJson = backStackEntry.arguments?.getString("photoItem")
                    val photoItem = Gson().fromJson(photoItemJson, FlickrItem::class.java)
                    PhotoDetails(photoItem)
                }
            }
        }
    )
}