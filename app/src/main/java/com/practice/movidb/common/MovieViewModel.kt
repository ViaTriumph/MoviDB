package com.practice.movidb.common

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.*
import com.practice.movidb.adapter.SearchMovieAdapter
import com.practice.movidb.network.common.ResultApiModel
import com.practice.movidb.network.movie.domain.MovieRepository
import com.practice.movidb.network.movie.domain.model.Result
import com.practice.movidb.network.search.domain.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.IllegalArgumentException
import javax.inject.Inject
import kotlin.random.Random

class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val searchRepository: SearchRepository
) : ViewModel() {

    val adapter = SearchMovieAdapter()
    var popularMovies = listOf<Result>()

    init {
        fetchPopularMovies()
    }

    fun addViewOperator(view: SearchView){
        addOperators(view)
    }

    private fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getPopularMovies().collect { it ->
                when (it) {
                    is ResultApiModel.Success -> {
                        popularMovies =  it.data?.results ?: listOf()
                        withContext(Dispatchers.Main) {
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

    private suspend fun fetchSearch(query: String) = searchRepository.getSearchResults(query)

    private fun addOperators(view: SearchView) {
        viewModelScope.launch {
            view.getQueryTextChangeStateFlow()
                .debounce(300)
                .filter { query ->
                    if (query.isEmpty() && query.length < 2) {
                        withContext(Dispatchers.Main) {
                            adapter.submitList(popularMovies)
                        }
                        return@filter false
                    } else {
                        return@filter true
                    }
                }.distinctUntilChanged()
                .flatMapLatest { query ->
                    fetchSearch(query)
                }
                .flowOn(Dispatchers.IO)
                .collect { response ->
                    when (response) {
                        is ResultApiModel.Success -> {
                            adapter.submitList(response.data?.results ?: listOf())
                        }
                        else -> adapter.submitList(popularMovies)
                    }
                }
        }
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

fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String> {
    val query = MutableStateFlow("")

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(query: String?): Boolean {
            return true
        }

        override fun onQueryTextSubmit(newText: String?): Boolean {
            query.value = newText ?: ""
            return true
        }
    })

    return query
}