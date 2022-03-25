package com.practice.movidb.ui.explore

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.practice.movidb.adapter.MovieAdapter
import com.practice.movidb.common.BaseResult
import com.practice.movidb.network.common.Result
import com.practice.movidb.network.movie.domain.MovieRepository
import com.practice.movidb.shared.domain.explore.NowPlayingUseCase
import com.practice.movidb.shared.domain.explore.PopularMoviesUseCase
import com.practice.movidb.shared.domain.movie.MovieList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExploreViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val popularMoviesUseCase = PopularMoviesUseCase(repository, Dispatchers.IO)
    private val nowPlayingUseCase = NowPlayingUseCase(repository, Dispatchers.IO)
    private val popularMovieAdapter = MovieAdapter()
    private val nowPlayingAdapter = MovieAdapter()

    fun init() {
        fetchPopularMovies()
        fetchNowPlayingMovies()
    }

    fun getPopularMoviesAdapter() = popularMovieAdapter
    fun getNowPlayingAdapter() = nowPlayingAdapter

    private fun fetchPopularMovies() {
        viewModelScope.launch {
            popularMoviesUseCase.invoke(Unit).collect { result ->
                processPopularMovies(result)
            }
        }
    }

    private fun fetchNowPlayingMovies() {
        viewModelScope.launch {
            nowPlayingUseCase.invoke(Unit).collect { result ->
                processNowPlayingMovies(result)
            }
        }
    }

    private fun processPopularMovies(result: BaseResult<MovieList>) {
        when (result) {
            is Result.Success -> {
                Log.d("EXPLORE_LOG", "${result.mData?.results}")
                popularMovieAdapter.submitList(result.mData?.results) //TODO improve
            }
        }
    }

    private fun processNowPlayingMovies(result: BaseResult<MovieList>) {
        when (result) {
            is Result.Success -> {
                Log.d("EXPLORE_LOG", "${result.mData?.results}")
                nowPlayingAdapter.submitList(result.mData?.results) //TODO improve
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ExploreViewModelFactory(private val repository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExploreViewModel::class.java)) {
            return ExploreViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class:" + modelClass.name)
    }
}