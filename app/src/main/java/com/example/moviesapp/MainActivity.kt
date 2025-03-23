package com.example.moviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.moviesapp.domain.model.Genre
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.presentation.MoviesScreen
import com.example.moviesapp.presentation.MoviesViewModel
import com.example.moviesapp.presentation.general.GenresRow
import com.example.moviesapp.presentation.general.MovieCard
import com.example.moviesapp.presentation.general.MovieGrid
import com.example.moviesapp.ui.theme.MoviesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewModel: MoviesViewModel = hiltViewModel()
            val genresList = viewModel.genres.collectAsState(initial = emptyList()).value

            LaunchedEffect(genresList) {
                if (genresList.isNotEmpty()) {
                    viewModel.getMoviesByGenre(genresList.first())
                }
            }

            MoviesAppTheme {
                MoviesScreen(
                    genresList = genresList,
                    moviesState = viewModel.moviesState.value,
                    imageConfigState = viewModel.imageConfigState.value,
                    getMoviesByGenre = { genre ->
                        viewModel.getMoviesByGenre(genre)
                    })
            }
        }
    }
}

