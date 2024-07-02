package com.app.flickrapp.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.app.flickrapp.viewmodel.FlickrViewModel
import com.google.gson.Gson
/**
 * [SearchScreen] displays a search interface for finding photos using the [FlickrViewModel].
 * It provides a text field for entering search terms, displays a grid of photo results,
 * and shows loading or error messages as needed.
 *
 * @param navController [NavController] used for navigating to the photo details screen.
 * @param viewModel [FlickrViewModel] instance for managing the state of the photo search.
 */
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: FlickrViewModel = hiltViewModel()
) {

    val photos by viewModel.photos.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    var searchText by remember { mutableStateOf("") }

    // composable layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // search text Field
        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                viewModel.searchPhotos(it) //Trigger photo search on text change
            },
            label = { Text("Search here...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .semantics { contentDescription = "Search Input" }, // For Talkback support
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search // Set action button to "Search"
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    viewModel.searchPhotos(searchText)
                }
            )
        )
        // Display loading spinner while fetching data
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (error != null) {
            // Display error message if there is an error
            Text(
                error!!,
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            //// Display a grid of photos or a message if there are no results
            if (photos.isNotEmpty()) {
                LazyVerticalGrid(GridCells.Adaptive(128.dp)) {
                    items(photos) { photo ->
                        Image(painter = rememberImagePainter(photo.media.m),
                            contentDescription = photo.title,
                            modifier = Modifier
                                .clickable {
                                    val photoItemJson = Uri.encode(Gson().toJson(photo))
                                    navController.navigate("details/$photoItemJson") // Navigate to photo details
                                }
                                .padding(4.dp)
                                .aspectRatio(1f)
                                .semantics { contentDescription = photo.title } // talkback support
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No results found", fontSize = 24.sp)
                }
            }
        }
    }

}