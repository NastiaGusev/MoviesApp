package com.example.moviesapp.presentation.general

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.moviesapp.R
import com.example.moviesapp.domain.model.Genre
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.presentation.MoviesState

@Composable
fun GenresRow(moviesState: MoviesState, genres: List<Genre>, onClick: (Genre) -> Unit) {

    LazyRow(modifier = Modifier) {
        items(count = genres.size) {
            val genre = genres[it]
            Text(
                text = genre.name,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .background(
                        color = if (moviesState.genreId == genre.id) colorResource(id = R.color.lightGray) else colorResource(
                            id = R.color.dark_gray
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
                    .clickable { onClick(genre) },
                color = Color.White
            )
        }
    }
}
