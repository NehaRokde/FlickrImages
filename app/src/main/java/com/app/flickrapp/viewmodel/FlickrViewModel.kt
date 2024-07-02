package com.app.flickrapp.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.flickrapp.data.model.FlickrItem
import com.app.flickrapp.repository.FlickrRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [FlickrViewModel] handles the UI-related data for the Flickr photo search feature.
 * It manages the state for fetching photos from the [FlickrRepository], including loading, error,
 * and the list of fetched photos.
 * This class is annotated with [@HiltViewModel] to enable dependency injection for the ViewModel
 * and provide instances of [FlickrRepository].
 * @param flickrRepository The [FlickrRepository] instance used to fetch photos from the Flickr API.
 * @param savedStateHandle The [SavedStateHandle] instance used for saving and restoring the state.
 */
@HiltViewModel
class FlickrViewModel @Inject constructor(
    private val flickrRepository: FlickrRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // The list of photos retrieved from the Flickr API.
    private val _photos = MutableStateFlow<List<FlickrItem>>(emptyList())
    val photos: StateFlow<List<FlickrItem>> get() = _photos

    // Indicates whether a photo search is currently in progress.
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    // Holds any error message from a failed photo search request.
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun searchPhotos(tags: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val response = flickrRepository.searchPhotos(tags)
                _photos.value = response.items
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

}