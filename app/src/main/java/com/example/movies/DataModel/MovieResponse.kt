package com.example.movies.DataModel

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Movie List") val MovieList: MutableList<Movie>
)