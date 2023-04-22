package com.example.movies.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movies.DataModel.Movie

@Dao
interface MovieDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<Movie>>

    @Delete
    fun deleteMovie(movie: Movie)
}