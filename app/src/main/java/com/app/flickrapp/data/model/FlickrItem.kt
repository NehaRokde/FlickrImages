package com.app.flickrapp.data.model

import com.google.gson.annotations.SerializedName

data class FlickrItem(@SerializedName("author")
                     val author: String,
                      @SerializedName("link")
                     val link: String,
                      @SerializedName("description")
                     val description: String,
                      @SerializedName("media")
                     val media: Media,
                      @SerializedName("published")
                     val published: String,
                      @SerializedName("title")
                     val title: String,
                      @SerializedName("author_id")
                     val authorId: String,
                      @SerializedName("date_taken")
                     val dateTaken: String,
                      @SerializedName("tags")
                     val tags: String)