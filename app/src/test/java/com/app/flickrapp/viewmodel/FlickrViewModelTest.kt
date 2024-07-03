package com.app.flickrapp.viewmodel

import org.junit.Assert.*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.app.flickrapp.data.model.FlickrItem
import com.app.flickrapp.data.model.FlickrResponse
import com.app.flickrapp.data.model.Media
import com.app.flickrapp.repository.FlickrRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class FlickrViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var flickrRepository: FlickrRepository

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: FlickrViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        savedStateHandle = SavedStateHandle()
        viewModel = FlickrViewModel(flickrRepository, savedStateHandle)
        Dispatchers.setMain(testDispatcher)
    }


    @Test
    fun `searchPhotos retrieves photos successfully`() = runTest {
        val tags = "dogs"
        val photos =   listOf(
            FlickrItem(
                title = "Orange Choppers",
                link = "https://www.flickr.com/photos/mtsofan/53818972996/",
                media = Media(m = "https://live.staticflickr.com/65535/53818972996_69ed98cbc9_m.jpg"),
                dateTaken = "2024-06-27T06:12:31-08:00",
                description = """
                    <p><a href="https://www.flickr.com/people/mtsofan/">MTSOfan</a> posted a photo:</p>
                    <p><a href="https://www.flickr.com/photos/mtsofan/53818972996/" title="Orange Choppers">
                    <img src="https://live.staticflickr.com/65535/53818972996_69ed98cbc9_m.jpg" width="240" height="194" alt="Orange Choppers" /></a></p>
                    <p>Here's a closer look at those porcupine teeth.<br /> <br /> According to the zoo's website, there are "almost thirty" species of porcupine. Each animal's body is covered with about 30,000 quills.</p>
                """.trimIndent(),
                published = "2024-06-27T10:17:53Z",
                author = "nobody@flickr.com (\"MTSOfan\")",
                authorId = "8628862@N05",
                tags = "porcupine teeth orange epz erethizondorsatum rodent nikonz5"
            )
        )
        val flickrResponse = FlickrResponse(
            link = "link",
            description = "description",
            modified = "modified",
            generator = "generator",
            title = "title",
            items = photos
        )
        `when`(flickrRepository.searchPhotos(tags)).thenReturn(flickrResponse)

        viewModel.searchPhotos(tags)
        advanceUntilIdle()

        assertEquals(photos, viewModel.photos.first())
        assertNull(viewModel.error.first())
    }

    @Test
    fun `searchPhotos sets loading to true and false correctly`() = runTest {
        val tags = "cats"
        val flickrResponse = FlickrResponse(
            link = "link",
            description = "description",
            modified = "modified",
            generator = "generator",
            title = "title",
            items = emptyList()
        )
        `when`(flickrRepository.searchPhotos(tags)).thenReturn(flickrResponse)

        val job = launch {
            viewModel.searchPhotos(tags)
        }

        assertTrue(viewModel.loading.first())
        job.join()
        assertFalse(viewModel.loading.first())
    }

    @Test
    fun `initial state is correct`() = runTest {
        assertTrue(viewModel.photos.first().isEmpty())
        assertFalse(viewModel.loading.first())
        assertNull(viewModel.error.first())
    }
}
