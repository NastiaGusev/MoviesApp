package com.example.moviesapp.presentation.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.presentation.general.ImageConfigState

@Composable
fun MovieGrid(
    imageConfigState: ImageConfigState,
    movies: LazyPagingItems<Movie>,
    onClick: (Movie) -> Unit
) {

    val handlePagingResult = handlePagingResult(movies = movies)
    if (handlePagingResult) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(count = movies.itemCount) {
                movies[it]?.let { movie ->
                    MovieCard(
                        imageConfigState = imageConfigState,
                        movie = movie,
                        onClick = {
                            onClick(movie)
                        })
                }
            }
        }
    }

}

@Composable
fun handlePagingResult(movies: LazyPagingItems<Movie>): Boolean {
    val loadState = movies.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }

        error != null -> {
            false
        }

        else -> true
    }
}

@Composable
private fun ShimmerEffect() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(count = 10) {
            MovieCardShimmerEffect(
                modifier = Modifier.padding(horizontal = 24.dp)
            )
        }
    }
}