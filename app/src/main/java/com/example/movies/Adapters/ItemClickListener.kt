package com.example.movies.Adapters

import android.view.View
import com.example.movies.DataModel.Movie

interface ItemClickListener {
    fun onItemClick(view: View, movie: Movie)
    fun reachedEnd(movie: Movie)
}