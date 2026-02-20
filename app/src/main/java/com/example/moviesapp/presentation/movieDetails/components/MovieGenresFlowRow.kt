package com.example.moviesapp.presentation.movieDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.domain.model.Genre
import com.example.moviesapp.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MovieGenresFlowRow(genres: List<Genre>, genreIds: List<Int>) {
    val genres = getGenreNamesByIds(genreIds, genres)

    FlowRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        genres.forEach { genre ->
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                colorResource(R.color.lightGray),
                                colorResource(R.color.lightestGray)
                            )
                        ),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .border(1.dp, colorResource(R.color.white), RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = genre,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        shadow = Shadow(
                            color = colorResource(R.color.lightGray),
                            offset = Offset(2f, 2f),
                            blurRadius = 3f
                        )
                    )
                )
            }
        }
    }
}

fun getGenreNamesByIds(genreIds: List<Int>, allGenres: List<Genre>): List<String> {
    return genreIds.mapNotNull { id -> allGenres.find { it.id == id }?.name }
}