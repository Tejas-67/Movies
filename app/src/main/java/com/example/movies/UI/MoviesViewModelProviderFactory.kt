package com.example.movies.UI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movies.DataModel.MovieRepository

class MoviesViewModelProviderFactory(val movieRepo: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MoviesViewModel(movieRepo) as T
    }
}