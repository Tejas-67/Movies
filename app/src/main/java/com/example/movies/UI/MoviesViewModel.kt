package com.example.movies.UI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.API.Resource
import com.example.movies.DataModel.Movie
import com.example.movies.DataModel.MovieRepository
import com.example.movies.DataModel.MovieResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class MoviesViewModel (val movieRepo: MovieRepository): ViewModel(){


     val movieList1: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()
     val movieList2: MutableLiveData<Resource<MovieResponse>> = MutableLiveData()

        val allDataInLocal: LiveData<List<Movie>> = movieRepo.allDataInLocal


    fun deleteFromLocal(movie: Movie){
        viewModelScope.launch{
            movieRepo.deleteFromLocal(movie)
        }
    }

    fun addToLocal(movie: Movie){
        viewModelScope.launch {
            movieRepo.addToLocal(movie)
        }
    }
     init{
         getPage1Movies()
         getPage2Movies()
     }
    fun getPage2Movies(){
        viewModelScope.launch{
            movieList2.postValue(Resource.Loading())
            val response=movieRepo.getMoviesPage2()
           movieList2.postValue(handleResponse(response))
        }
    }
     fun getPage1Movies(){
         viewModelScope.launch{
             movieList1.postValue(Resource.Loading())
             val response=movieRepo.getMoviesPage1()
             movieList1.postValue(handleResponse(response))
         }
     }

    private fun handleResponse(response: Response<MovieResponse>) : Resource<MovieResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleResponse2(response: Response<MovieResponse>) : Resource<MovieResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}