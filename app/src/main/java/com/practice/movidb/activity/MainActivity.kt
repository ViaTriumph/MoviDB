package com.practice.movidb.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practice.movidb.MyApplication
import com.practice.movidb.R
import com.practice.movidb.adapter.SearchMovieAdapter
import com.practice.movidb.common.MovieViewModel
import com.practice.movidb.common.MovieViewModelFactory
import com.practice.movidb.di.BaseComponent
import com.practice.movidb.network.movie.domain.MovieRepository
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var baseComponent: BaseComponent

    @Inject lateinit var movieRepository: MovieRepository

    private val movieViewModel: MovieViewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(movieRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        baseComponent = (application as MyApplication).baseComponent
        baseComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.searchResultsRcv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = movieViewModel.adapter

    }
}