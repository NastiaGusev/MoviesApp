package com.example.moviesapp.presentation

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.moviesapp.domain.model.Genre
import com.example.moviesapp.domain.repository.MoviesRepository
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val application: Application
) : ViewModel() {

    private val _moviesState = mutableStateOf(MoviesState())
    val moviesState: State<MoviesState> = _moviesState

    private val _imageConfigState = mutableStateOf(ImageConfigState())
    val imageConfigState: State<ImageConfigState> = _imageConfigState

    val genres = flow {
        emit(moviesRepository.getGenres())
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        getConfiguration()
    }

    private fun getConfiguration() {
        viewModelScope.launch {
            val config = moviesRepository.getConfiguration()
            val bestPosterSize = pickBestSize(config.poster_sizes)

            _imageConfigState.value = ImageConfigState(
                baseUrl = config.secure_base_url,
                posterSize = bestPosterSize
            )
        }
    }

    private fun pickBestSize(sizes: List<String>): String {
        val screenWidthPx = application.resources.displayMetrics.widthPixels
        val numericSizes = sizes.mapNotNull { it.removePrefix("w").toIntOrNull() }
        if (numericSizes.isEmpty()) return sizes.first()

        val optimalSize = numericSizes.firstOrNull { it >= screenWidthPx / 3 }
        return "w${optimalSize ?: numericSizes.first()}"
    }

    fun getMoviesByGenre(genre: Genre) {
        _moviesState.value = moviesState.value.copy(genreId = genre.id)
        val movies = moviesRepository.getMovies(
            genreId = moviesState.value.genreId
        ).cachedIn(viewModelScope)
        _moviesState.value = moviesState.value.copy(movies = movies)
    }
}