package com.example.movies.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.movies.DataModel.MovieRepository
import com.example.movies.Database.MovieDatabase
import com.example.movies.R
import com.example.movies.UI.MoviesViewModel
import com.example.movies.UI.MoviesViewModelProviderFactory
import com.example.movies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding?=null
    private val binding get()=_binding!!
    lateinit var viewModel: MoviesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val repo=MovieRepository(MovieDatabase.getDatabase(this))
        val VMF= MoviesViewModelProviderFactory(repo)
        viewModel=ViewModelProvider(this, VMF).get(MoviesViewModel::class.java)

    }
}