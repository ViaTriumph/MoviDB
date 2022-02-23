package com.practice.movidb.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.practice.movidb.adapter.SearchMovieAdapter
import com.practice.movidb.network.common.ResultApiModel
import com.practice.movidb.network.movie.domain.MovieRepository
import com.practice.movidb.network.movie.domain.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

    val adapter = SearchMovieAdapter()
    var popularMovies = listOf<Result>()
    init {
        fetchPopularMovies()
    }

    private fun fetchPopularMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getPopularMovies().collect{
                when(it){
                    is ResultApiModel.Success -> {
                        popularMovies = it.data?.results ?: listOf()
                        withContext(Dispatchers.Main){
                            adapter.submitList(popularMovies)
                        }
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