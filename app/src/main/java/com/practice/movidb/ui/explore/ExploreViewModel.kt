package com.practice.movidb.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.practice.movidb.common.BaseViewModel
import com.practice.shared.data.network.BaseResult
import com.practice.shared.domain.explore.NowPlayingUseCase
import com.practice.shared.domain.explore.PopularMoviesUseCase
import com.practice.shared.domain.movie.MovieList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExploreViewModel @Inject constructor(
    private val popularMoviesUseCase: PopularMoviesUseCase,
    private val nowPlayingUseCase: NowPlayingUseCase
    ) : BaseViewModel() {

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
            is com.practice.shared.data.network.Result.Success -> {
                popularMovieModel.populateList(result.data?.results) //TODO improve
            }
        }
    }

    private fun processNowPlayingMovies(result: BaseResult<MovieList>) {
        when (result) {
            is com.practice.shared.data.network.Result.Success -> {
                nowPlayingModel.populateList(result.data?.results) //TODO improve
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ExploreViewModelFactory(
    private val popularMoviesUseCase: PopularMoviesUseCase,
    private val nowPlayingUseCase: NowPlayingUseCase
    ): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExploreViewModel::class.java)) {
            return ExploreViewModel(popularMoviesUseCase, nowPlayingUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class:" + modelClass.name)
    }
}