package com.app.flickrapp.ui.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import coil.compose.rememberImagePainter
import com.app.flickrapp.data.model.FlickrItem
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * [PhotoDetails] displays detailed information about a specific photo.
 * It includes the photo image, title, author, description, publication date, dimensions, and a share button.
 */
@Composable
fun PhotoDetails(photo: FlickrItem) {

    val context = LocalContext.current
    val dimensions = extractDimensions(photo.description)

    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val formattedDate = try {
        val date = dateFormat.parse(photo.published)
        SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault()).format(date)
    } catch (e: Exception) {
        "Unknown"
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display photo image
        Image(
            painter = rememberImagePainter(data = photo.media.m),
            contentDescription = photo.title,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .semantics { contentDescription = "Photo" } // talkback support
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Display photo title
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Title: ",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = photo.title,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Display photo Author
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Author: ",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = photo.author,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Display photo Description
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Description: ",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            HtmlText(
                html = photo.description,
                fontSize = 18
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Display photo Published
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Published: ",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = formattedDate,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Display photo Dimesions
        Row(modifier = Modifier.fillMaxWidth()) {
            dimensions?.let {
                Text(
                    text = "Dimensions: ${it.first} x ${it.second}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Dispaly Photo Share Button
        Button(modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .semantics { contentDescription = "Share Button" }, // talkback support
            onClick = {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "${photo.title} - ${photo.link}")
                type = "text/plain"
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share via"))
        }) {
            Text("Share")
        }
    }
}


/**
 * Extracts width and height from the HTML description string.
 */
fun extractDimensions(description: String): Pair<Int, Int>? {
    val pattern = Pattern.compile("width=\"(\\d+)\" height=\"(\\d+)\"")
    val matcher = pattern.matcher(description)
    return if (matcher.find()) {
        Pair(matcher.group(1).toInt(), matcher.group(2).toInt())
    } else {
        null
    }
}

/**
 * [HtmlText] displays description HTML content as text.
 */
@Composable
fun HtmlText(html: String, fontSize: Int) {
    val spanned = remember { HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT) }
    Text(
        text = buildAnnotatedString { append(spanned.toString()) },
        fontSize = fontSize.sp
    )
}
