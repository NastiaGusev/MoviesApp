package com.example.moviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviesapp.presentation.movies.MoviesViewModel
import com.example.moviesapp.presentation.navigation.MoviesNavGraph
import com.example.moviesapp.ui.theme.MoviesAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val viewModel: MoviesViewModel = hiltViewModel()
            val genresList = viewModel.genres.collectAsState(initial = emptyList()).value

            LaunchedEffect(genresList) {
                if (genresList.isNotEmpty()) {
                    viewModel.getMoviesByGenre(genresList.first())
                }
            }

            MoviesAppTheme {
                val systemController = rememberSystemUiController()

                SideEffect {
                    systemController.setSystemBarsColor(
                        color = Color.Transparent,
                        darkIcons = false
                    )
                }

                MoviesNavGraph(viewModel = viewModel, genresList = genresList)
            }
        }
    }
}

