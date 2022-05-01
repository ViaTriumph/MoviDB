package com.practice.movidb.ui.search

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.practice.movidb.ui.UIUtils
import com.practice.shared.data.network.BaseResult
import com.practice.shared.domain.movie.Movie
import com.practice.shared.domain.movie.MovieList
import com.practice.shared.domain.search.SearchUseCase
import com.practice.shared.domain.search.SearchUseCaseParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val searchUseCase: SearchUseCase) : ViewModel() {

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
                .collect { query ->
                    fetchSearchResults(query)
                }
        }
    }

    private fun fetchSearchResults(query: String) {
        val params = getSearchUseCaseParams(query)
        searchJob?.cancel()
        searchJob =
            viewModelScope.launch { // Use main dispatcher (it is the default dispatcher in viewmodel)
                searchUseCase.invoke(params).collect { result ->
                    processSearchResult(result)
                }
            }
    }

    private fun processSearchResult(result: BaseResult<MovieList>) {
        when (result) {
            is com.practice.shared.data.network.Result.Success -> {
                val list: List<Movie> = result.data?.results ?: emptyList()
                searchAdapter.submitList(UIUtils.convertToPresentation(list))
            }
        }
    }

    fun getAdapter() = searchAdapter

    private fun getSearchUseCaseParams(query: String, page: Int = 1) = SearchUseCaseParams(
        query = query,
        adult = null,//Settings.getAdult(),
        language = null,//Settings.getLanguage(),
        page = page,
        region = null,//Settings.getRegion(),
        releaseYear = null
    )
}

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(private val searchUseCase: SearchUseCase) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(searchUseCase) as T
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