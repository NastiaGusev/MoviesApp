package com.example.moviesapp.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.moviesapp.R
import com.example.moviesapp.domain.model.Genre
import com.example.moviesapp.presentation.movieDetails.MovieDetailsScreen
import com.example.moviesapp.presentation.movies.MoviesScreen
import com.example.moviesapp.presentation.movies.MoviesViewModel
import kotlinx.coroutines.launch

@Composable
fun MoviesNavGraph(viewModel: MoviesViewModel, genresList: List<Genre>) {
    val navController = rememberNavController()
    var gridState = rememberLazyGridState()

    NavHost(
        navController = navController,
        startDestination = MoviesScreenObj
    ) {

        composable<MoviesScreenObj> {
            val coroutineScope = rememberCoroutineScope()

            Box(modifier = Modifier.background(colorResource(id = R.color.black))) {
                MoviesScreen(
                    genresList = genresList,
                    moviesState = viewModel.moviesState.value,
                    imageConfigState = viewModel.imageConfigState.value,
                    getMoviesByGenre = { genre ->
                        viewModel.getMoviesByGenre(genre)
                        coroutineScope.launch {
                            gridState.scrollToItem(0)
                        }
                    },
                    onClickMovie = { movieId ->
                        navController.navigate(
                            MovieDetailsScreenObj(
                                movieId = movieId
                            )
                        )
                    },
                    gridState = gridState
                )
            }
        }

        composable<MovieDetailsScreenObj> {
            val args = it.toRoute<MovieDetailsScreenObj>()

            viewModel.moviesState.value.movies?.let {
                val movies = it.collectAsLazyPagingItems()
                val movie = movies.itemSnapshotList.items.find { it.id == args.movieId }

                movie?.let {
                    MovieDetailsScreen(
                        movie = movie,
                        genres = genresList,
                        imageConfigState = viewModel.imageConfigState.value,
                        navigateUp = { navController.navigateUp() },
                    )
                }
            }
        }
    }
}