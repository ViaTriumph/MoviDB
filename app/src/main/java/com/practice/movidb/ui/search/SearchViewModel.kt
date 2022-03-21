package com.practice.movidb.ui.search

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.practice.movidb.adapter.SearchMovieAdapter
import com.practice.movidb.common.BaseResult
import com.practice.movidb.network.common.Result
import com.practice.movidb.network.movie.domain.model.Movie
import com.practice.movidb.network.search.domain.SearchRepository
import com.practice.movidb.network.search.domain.model.SearchMovieList
import com.practice.movidb.shared.common.Settings
import com.practice.movidb.shared.domain.search.SearchUseCase
import com.practice.movidb.shared.domain.search.SearchUseCaseParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(repository: SearchRepository) : ViewModel() {

    //TODO inject
    private val searchUseCase = SearchUseCase(repository, Dispatchers.IO)

    private val searchAdapter = SearchMovieAdapter()

    private var searchJob: Job? = null

    fun observeSearchInput(view: SearchView) {
        viewModelScope.launch {
            view.getQueryTextChangeStateFlow()
                .debounce(300)
                .filter { query ->
                    return@filter !(query.isEmpty() && query.length < 2)
                }
                .distinctUntilChanged()
                .flowOn(Dispatchers.Main)
                .collectLatest { query ->
                    fetchSearchResults(query)
                }
        }
    }

    private fun fetchSearchResults(query: String) {
        val params = getSearchUseCaseParams(query)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            searchUseCase.invoke(params).collect { result ->
                processSearchResult(result)
            }
        }
    }

    private fun processSearchResult(result: BaseResult<SearchMovieList>) {
        when (result) {
            is Result.Success -> {
                val list: List<Movie> = result.data?.results ?: emptyList()
                searchAdapter.submitList(list)
            }
        }
    }

    fun getAdapter() = searchAdapter

    private fun getSearchUseCaseParams(query: String, page: Int = 0) = SearchUseCaseParams(
        query = query,
        adult = Settings.getAdult(),
        language = Settings.getLanguage(),
        page = page,
        region = Settings.getRegion(),
        releaseYear = null
    )
}

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(private val repository: SearchRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}

private fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String> {
    val query = MutableStateFlow("")

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextChange(q: String?): Boolean {
            query.value = q ?: ""
            return true
        }

        override fun onQueryTextSubmit(newText: String?): Boolean {
            return true
        }
    })

    return query
}