package com.example.moviesapp.presentation.movies.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.domain.model.Movie
import com.example.moviesapp.R
import com.example.moviesapp.presentation.general.ImageConfigState
import com.example.moviesapp.presentation.general.ScoreCircle
import com.example.moviesapp.presentation.general.shimmerEffect
import com.example.moviesapp.ui.theme.MoviesAppTheme
import com.example.moviesapp.util.getYearFromDate

@Composable
fun MovieCard(
    imageConfigState: ImageConfigState,
    movie: Movie,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.clickable { onClick() }) {

        Box(modifier = Modifier) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.7f),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.dark_gray)),
                shape = RoundedCornerShape(15.dp),
                elevation = CardDefaults.cardElevation(5.dp)
            ) {
                AsyncImage(
                    model = "${imageConfigState.baseUrl}${imageConfigState.posterSize}${movie.posterPath}",
                    contentDescription = movie.title,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillBounds
                )
            }

            ScoreCircle(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 2.dp)
                    .size(50.dp)
                    .offset(y = 15.dp),
                voteAverage = movie.voteAverage,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp, bottom = 20.dp)
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally),

            ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = movie.title,
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = colorResource(R.color.white)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = getYearFromDate(movie.releaseDate),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = colorResource(R.color.lightGray)
            )
        }
    }
}

@Composable
fun MovieCardShimmerEffect(modifier: Modifier) {
    Column(modifier = modifier.padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 20.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.7f)
                .clip(MaterialTheme.shapes.medium)
                .shimmerEffect(),
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .shimmerEffect(),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMovieCardShimmerEffect() {
    MoviesAppTheme {
        MovieCardShimmerEffect(
            modifier = Modifier
        )
    }
}

