package com.app.flickrapp.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import com.app.flickrapp.viewmodel.FlickrViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class SearchScreenKtTest{
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: FlickrViewModel
    private lateinit var navController: NavController

    @Before
    fun setup() {
        // Mock the FlickrViewModel and NavController
        viewModel = mock(FlickrViewModel::class.java)
        navController = mock(NavController::class.java)

        // Setup default return values
        `when`(viewModel.loading).thenReturn(MutableStateFlow(false))
        `when`(viewModel.error).thenReturn(MutableStateFlow(null))
        `when`(viewModel.photos).thenReturn(MutableStateFlow(emptyList()))

        composeTestRule.setContent {
            SearchScreen(navController = navController, viewModel = viewModel)
        }
    }

    @Test
    fun searchFieldExistsAndUpdatesText() {
        // Check if the search field is present
        composeTestRule.onNodeWithContentDescription("Search Input").assertExists()

        // Perform a text input action
        composeTestRule.onNodeWithContentDescription("Search Input").performTextInput("porcupine")

        // Verify that the viewModel's searchPhotos function is called
        verify(viewModel).searchPhotos("porcupine")
    }

    @Test
    fun showsLoadingSpinnerWhenLoadingIsTrue() {
        // Set loading to true
        `when`(viewModel.loading).thenReturn(MutableStateFlow(true))

        composeTestRule.setContent {
            SearchScreen(navController = navController, viewModel = viewModel)
        }

        // Check if the loading spinner is displayed
        composeTestRule.onNodeWithTag("loading_spinner").assertExists()
    }

    @Test
    fun showsNoResultsMessageWhenThereAreNoPhotos() {
        // Set empty photos
        `when`(viewModel.photos).thenReturn(MutableStateFlow(emptyList()))

        composeTestRule.setContent {
            SearchScreen(navController = navController, viewModel = viewModel)
        }

        // Check if the "No results found" message is displayed
        composeTestRule.onNodeWithText("No results found").assertExists()
    }
}