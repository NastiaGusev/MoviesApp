package com.example.moviesapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.moviesapp.R
import com.example.moviesapp.domain.model.Genre
import com.example.moviesapp.presentation.general.GenresRow
import com.example.moviesapp.presentation.general.MovieGrid

@Composable
fun MoviesScreen(
    genresList: List<Genre>,
    moviesState: MoviesState,
    imageConfigState: ImageConfigState,
    getMoviesByGenre: (Genre) -> Unit
) {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.black))
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Movies",
            fontSize = 20.sp,
            color = Color.LightGray,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 20.dp)
                .fillMaxWidth()
        )

        GenresRow(
            moviesState = moviesState,
            genres = genresList,
            onClick = { genre ->
                getMoviesByGenre(genre)
            })

        Spacer(modifier = Modifier.height(16.dp))

        moviesState.movies?.let {
            val movies = it.collectAsLazyPagingItems()
            MovieGrid(
                imageConfigState = imageConfigState,
                movies = movies,
                onClick = {
                    //TODO: Add functionality for clicking movie
                },
            )
        }
    }
}