package com.app.flickrapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.flickrapp.data.model.FlickrItem
import com.app.flickrapp.viewmodel.FlickrViewModel

@Composable
fun MainScreen(viewModel: FlickrViewModel = hiltViewModel(), onImageClick : (FlickrItem) -> Unit){
    
}