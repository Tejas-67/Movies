package com.example.movies.DataModel

import androidx.lifecycle.LiveData
import com.example.movies.API.RetrofitInstance
import com.example.movies.Database.MovieDao
import com.example.movies.Database.MovieDatabase

class MovieRepository(val db: MovieDatabase){

    private val dao: MovieDao = db.getMovieDao()
    suspend fun getMoviesPage1()= RetrofitInstance.api.getPage1()

    val allDataInLocal: LiveData<List<Movie>> = dao.getAllMovies()

    suspend fun getMoviesPage2()=RetrofitInstance.api.getPage2()

    suspend fun addToLocal(movie: Movie){
        dao.insert(movie)
    }


    suspend fun deleteFromLocal(movie: Movie){
        dao.deleteMovie(movie)
    }
}

