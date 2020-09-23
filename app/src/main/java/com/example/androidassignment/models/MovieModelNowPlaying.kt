package com.example.androidassignment.models

data class MovieModelNowPlaying(
    val dates: DatesX,
    val page: Int,
    val results: ArrayList<MovieNowPlayingResult>,
    val total_pages: Int,
    val total_results: Int
)