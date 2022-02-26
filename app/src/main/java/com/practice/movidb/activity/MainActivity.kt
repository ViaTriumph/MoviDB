package com.practice.movidb.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practice.movidb.MyApplication
import com.practice.movidb.R
import com.practice.movidb.common.MovieViewModel
import com.practice.movidb.common.MovieViewModelFactory
import com.practice.movidb.databinding.ActivityMainBinding
import com.practice.movidb.di.BaseComponent
import com.practice.movidb.network.movie.domain.MovieRepository
import com.practice.movidb.network.search.domain.SearchRepository
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var baseComponent: BaseComponent
    private lateinit var binding: ActivityMainBinding

    @Inject lateinit var movieRepository: MovieRepository
    @Inject lateinit var searchRepository: SearchRepository

    private val movieViewModel: MovieViewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(movieRepository, searchRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        baseComponent = (application as MyApplication).baseComponent
        baseComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initBinding()

        val recyclerView = findViewById<RecyclerView>(R.id.searchResultsRcv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = movieViewModel.adapter

        val searchView = findViewById<SearchView>(R.id.searchEditText)
        movieViewModel.addViewOperator(searchView)

    }

    private fun initBinding(){
        binding.viewModel = movieViewModel
        binding.lifecycleOwner = this
    }
}