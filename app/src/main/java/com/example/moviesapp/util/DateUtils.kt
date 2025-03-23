package com.example.moviesapp.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun parseDate(dateString: String?): Date? {
    return try {
        // Legacy approach for older Android versions
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.parse(dateString ?: return null)
    } catch (e: Exception) {
        null
    }
}

fun getYearFromDate(dateString: String?): String {
    return try {
        val date = parseDate(dateString) ?: return ""
        val calendar = Calendar.getInstance().apply { time = date }
        calendar.get(Calendar.YEAR).toString()
    } catch (e: Exception) {
        ""
    }
}