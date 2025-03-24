package com.example.moviesapp.presentation.general

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.moviesapp.R
import java.util.Locale

@Composable
fun ScoreCircle(modifier: Modifier, voteAverage: Double) {
    Box(
        modifier = modifier
            .background(
                colorResource(R.color.dark_gray).copy(alpha = 0.7f),
                shape = CircleShape
            )
            .border(2.dp, getScoreColor(voteAverage), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(String.format(Locale.US, "%.1f", voteAverage))
                }
                withStyle(
                    style = SpanStyle(
                        color = colorResource(R.color.lightestGray),
                        fontWeight = FontWeight.Normal
                    )
                ) {
                    append("/10")
                }
            },
            color = Color.White,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun getScoreColor(score: Double): Color {
    return when {
        score >= 7.0 -> colorResource(id = R.color.score_green)
        score >= 5.0 -> colorResource(id = R.color.score_yellow)
        else -> colorResource(id = R.color.score_red)
    }
}
