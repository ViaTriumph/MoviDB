package com.practice.movidb.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.practice.movidb.network.common.BaseNetwork
import com.practice.movidb.network.common.ResultApiModel
import com.practice.movidb.network.movie.domain.MovieRepository
import com.practice.movidb.network.movie.service.MovieService
import com.practice.movidb.network.search.service.SearchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

    fun fetchPopularMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getPopularMovies().collect{
                when(it){
                    is ResultApiModel.Success -> {
                        Log.d("MOVIE_DB_", "${it.data}")
                    }
                    is ResultApiModel.Error -> {}
                    is ResultApiModel.Loading -> {}
                    is ResultApiModel.None -> {}
                }
            }
        }
    }
}

class MovieViewModelFactory(private val movieRepository: MovieRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(movieRepository) as T
        }
        throw IllegalArgumentException("Cannot convert to MovieViewModel")
    }
}