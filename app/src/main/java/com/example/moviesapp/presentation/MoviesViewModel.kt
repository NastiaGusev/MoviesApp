package com.example.moviesapp.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.moviesapp.domain.model.Genre
import com.example.moviesapp.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _moviesState = mutableStateOf(MoviesState())
    val moviesState: State<MoviesState> = _moviesState

    val genres = flow {
        emit(moviesRepository.getGenres())
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getMoviesByGenre(genre: Genre) {
        _moviesState.value = moviesState.value.copy(genreId = genre.id)
        val movies = moviesRepository.getMovies(
            genreId = moviesState.value.genreId
        ).cachedIn(viewModelScope)
        _moviesState.value = moviesState.value.copy(movies = movies)
    }
}