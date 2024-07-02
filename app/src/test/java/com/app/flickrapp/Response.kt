package com.app.flickrapp

import com.app.flickrapp.data.model.FlickrItem
import com.app.flickrapp.data.model.FlickrResponse
import com.app.flickrapp.data.model.Media

// Sample data creation function
fun createSampleFlickrResponse(): FlickrResponse {
    return FlickrResponse(
        title = "Recent Uploads tagged porcupine",
        link = "https://www.flickr.com/photos/tags/porcupine/",
        description = "",
        modified = "2024-06-27T10:17:53Z",
        generator = "https://www.flickr.com",
        items = listOf(
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
            ),
            FlickrItem(
                title = "Oregon Zoo June 2024",
                link = "https://www.flickr.com/photos/djwudi/53816721164/",
                media = Media(m = "https://live.staticflickr.com/65535/53816721164_8e39fa8ac1_m.jpg"),
                dateTaken = "2024-06-20T12:06:03-08:00",
                description = """
                    <p><a href="https://www.flickr.com/people/djwudi/">djwudi</a> posted a photo:</p>
                    <p><a href="https://www.flickr.com/photos/djwudi/53816721164/" title="Oregon Zoo June 2024">
                    <img src="https://live.staticflickr.com/65535/53816721164_8e39fa8ac1_m.jpg" width="240" height="160" alt="Oregon Zoo June 2024" /></a></p>
                    <p>A visit to the Oregon Zoo on a gorgeous sunny day.</p>
                """.trimIndent(),
                published = "2024-06-26T02:30:10Z",
                author = "nobody@flickr.com (\"djwudi\")",
                authorId = "35034356271@N01",
                tags = "70300f456 d750 nikon or oregon oregonzoo portland tamron usa unitedstates credit:photographer=michaelhanscom porcupine"
            )
        )
    )
}