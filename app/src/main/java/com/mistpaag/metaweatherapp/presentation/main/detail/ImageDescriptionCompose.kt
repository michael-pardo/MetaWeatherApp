package com.mistpaag.metaweatherapp.presentation.main.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import coil.compose.rememberImagePainter
import com.mistpaag.imagemanager.getMediaURL

@Composable
fun ImageDescriptionCompose(imageURL: String, temp: String, weatherStateName: String){
    Row {
        Image(
            painter = rememberImagePainter(imageURL.getMediaURL()),
            contentDescription = ""
        )
        Column {
            Text(text = temp)
            Text(text = weatherStateName)
        }
    }
}

@Composable
fun TextFromCompose(text: String) {
    Column {
        Text(text = "Compose Text")
        Text(text = text)
    }
}