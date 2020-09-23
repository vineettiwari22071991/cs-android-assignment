package com.example.androidassignment.models

data class MoviePopularModel(
    val dates: Dates,
    val page: Int,
    val results: ArrayList<MoviePopularResult>,
    val total_pages: Int,
    val total_results: Int
)