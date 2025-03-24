package com.example.moviesapp.presentation.movieDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviesapp.R
import com.example.moviesapp.domain.model.Genre
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.presentation.general.ImageConfigState
import com.example.moviesapp.presentation.general.ScoreCircle

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MovieDetailsScreen(
    movie: Movie,
    genres: List<Genre>,
    imageConfigState: ImageConfigState,
    navigateUp: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(colorResource(R.color.black))
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .background(colorResource(R.color.black))
        ) {
            AsyncImage(
                model = "${imageConfigState.baseUrl}${imageConfigState.backdropSize}${movie.backdrop_path}",
                contentDescription = movie.title,
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                contentScale = ContentScale.FillWidth
            )

            IconButton(
                onClick = { navigateUp() },
                modifier = Modifier
                    .padding(top = 30.dp, start = 18.dp)
                    .size(40.dp)
                    .background(Color.Black.copy(alpha = 0.7f), shape = CircleShape)
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back_arrow),
                    contentDescription = null,
                    tint = Color.White
                )
            }

            ScoreCircle(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp)
                    .size(50.dp)
                    .offset(y = (-20).dp),
                voteAverage = movie.vote_average,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = movie.title,
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = colorResource(R.color.white)
            )

            Spacer(modifier = Modifier.height(5.dp))

            val genres = getGenreNamesByIds(movie.genre_ids, genres)
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

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = movie.overview,
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal),
                color = colorResource(R.color.lightestGray)
            )
        }
    }
}

fun getGenreNamesByIds(genreIds: List<Int>, allGenres: List<Genre>): List<String> {
    return genreIds.mapNotNull { id -> allGenres.find { it.id == id }?.name }
}