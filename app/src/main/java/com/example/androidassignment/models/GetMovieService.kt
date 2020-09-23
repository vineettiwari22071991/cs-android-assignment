package com.example.androidassignment.models

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetMovieService {


    @GET("now_playing?language=en-US&page=undefined&api_key=55957fcf3ba81b137f8fc01ac5a31fb5")
    fun getMovie():Single<MovieModelNowPlaying>


    @GET("popular?api_key=55957fcf3ba81b137f8fc01ac5a31fb5&language=en-US&")
    fun getMoviePopular(@Query("page")no:Int):Single<MoviePopularModel>



    @GET("{id}?api_key=55957fcf3ba81b137f8fc01ac5a31fb5")
    fun getMovieDetail(@Path("id")id:Int):Single<MovieDetaiModel>






}