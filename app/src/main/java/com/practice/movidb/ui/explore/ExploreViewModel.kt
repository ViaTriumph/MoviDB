package com.practice.movidb.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.practice.movidb.common.BaseResult
import com.practice.movidb.common.BaseViewModel
import com.practice.movidb.network.common.Result
import com.practice.movidb.network.movie.domain.MovieRepository
import com.practice.movidb.shared.domain.explore.NowPlayingUseCase
import com.practice.movidb.shared.domain.explore.PopularMoviesUseCase
import com.practice.movidb.shared.domain.movie.MovieList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExploreViewModel @Inject constructor(private val repository: MovieRepository) :
    BaseViewModel() {

    private val popularMoviesUseCase = PopularMoviesUseCase(repository, Dispatchers.IO)
    private val nowPlayingUseCase = NowPlayingUseCase(repository, Dispatchers.IO)
    private val popularMovieModel = MovieModel(this)
    private val nowPlayingModel = MovieModel(this)

    fun init() {
        fetchPopularMovies()
        fetchNowPlayingMovies()
    }

    fun getPopularMoviesAdapter() = popularMovieModel.adapter
    fun getNowPlayingAdapter() = nowPlayingModel.adapter

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
                popularMovieModel.populateList(result.data?.results) //TODO improve
            }
        }
    }

    private fun processNowPlayingMovies(result: BaseResult<MovieList>) {
        when (result) {
            is Result.Success -> {
                nowPlayingModel.populateList(result.data?.results) //TODO improve
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