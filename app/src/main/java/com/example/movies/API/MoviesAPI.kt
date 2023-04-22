package com.example.movies.API

import android.graphics.Movie
import com.example.movies.DataModel.MovieResponse
import retrofit2.Response
import retrofit2.http.GET

interface MoviesAPI {

    @GET("1.json")
    suspend fun getPage1(): Response<MovieResponse>

    @GET("2.json")
    suspend fun getPage2(): Response<MovieResponse>
}