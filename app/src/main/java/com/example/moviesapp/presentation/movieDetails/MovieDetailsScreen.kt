package com.example.moviesapp.presentation.movieDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviesapp.R
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.presentation.general.ImageConfigState

@Composable
fun MovieDetailsScreen(
    movie: Movie,
    imageConfigState: ImageConfigState
) {
    Column(modifier = Modifier
        .background(colorResource(R.color.black))
        .fillMaxSize()) {
        AsyncImage(
            model = "${imageConfigState.baseUrl}${imageConfigState.backdropSize}${movie.backdrop_path}",
            contentDescription = movie.title,
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
            contentScale = ContentScale.FillWidth
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = movie.title,
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = colorResource(R.color.white)
        )
    }
}