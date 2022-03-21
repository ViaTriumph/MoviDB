package com.practice.movidb.common

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practice.movidb.adapter.SearchMovieAdapter
import com.practice.movidb.network.movie.domain.MovieRepository
import com.practice.movidb.network.movie.domain.model.Movie
import com.practice.movidb.network.search.domain.SearchRepository
import javax.inject.Inject

class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val searchRepository: SearchRepository
) : ViewModel() {

    val adapter = SearchMovieAdapter()
    var popularMovies = listOf<Movie>()

    init {
        fetchPopularMovies()
    }

    fun addViewOperator(view: SearchView){
        addOperators(view)
    }

    private fun fetchPopularMovies() {
//        viewModelScope.launch(Dispatchers.IO) {
//            movieRepository.getPopularMovies().collect { it ->
//                when (it) {
//                    is ResponseModel.Success -> {
//                        popularMovies =  it.data?.results ?: listOf()
//                        withContext(Dispatchers.Main) {
//                            adapter.submitList(popularMovies)
//                        }
//                    }
//                    is ResponseModel.Error -> {}
//                    is ResponseModel.Loading -> {}
//                    is ResponseModel.None -> {}
//                }
//            }
//        }
    }


    private fun addOperators(view: SearchView) {
//        viewModelScope.launch {
//            view.getQueryTextChangeStateFlow()
//                .debounce(300)
//                .filter { query ->
//                    if (query.isEmpty() && query.length < 2) {
//                        withContext(Dispatchers.Main) {
//                            adapter.submitList(popularMovies)
//                        }
//                        return@filter false
//                    } else {
//                        return@filter true
//                    }
//                }.distinctUntilChanged()
//                .flatMapLatest { query ->
//                    fetchSearch(query)
//                }
//                .flowOn(Dispatchers.IO)
//                .collect { response ->
//                    when (response) {
//                        is ResponseModel.Success -> {
//                            adapter.submitList(response.data?.results ?: listOf())
//                        }
//                        else -> adapter.submitList(popularMovies)
//                    }
//                }
//        }
    }
}

class MovieViewModelFactory(
    private val movieRepository: MovieRepository,
    private val searchRepository: SearchRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(movieRepository, searchRepository) as T
        }
        throw IllegalArgumentException("Cannot convert to MovieViewModel")
    }
}