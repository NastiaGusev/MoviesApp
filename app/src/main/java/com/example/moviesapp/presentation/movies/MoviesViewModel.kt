package com.example.moviesapp.presentation.movies

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.filter
import com.example.moviesapp.domain.model.Genre
import com.example.moviesapp.domain.repository.MoviesRepository
import com.example.moviesapp.presentation.general.ImageConfigState
import com.example.moviesapp.presentation.movies.MoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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
    }.stateIn(viewModelScope, SharingStarted.Companion.Lazily, emptyList())

    init {
        getConfiguration()
    }

    private fun getConfiguration() {
        viewModelScope.launch {
            val config = moviesRepository.getConfiguration()
            val bestPosterSize = pickBestSize(config.poster_sizes)
            val bestBackDropSize = pickBestSize(config.backdrop_sizes)

            _imageConfigState.value = ImageConfigState(
                baseUrl = config.secure_base_url,
                posterSize = bestPosterSize,
                backdropSize = bestBackDropSize

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