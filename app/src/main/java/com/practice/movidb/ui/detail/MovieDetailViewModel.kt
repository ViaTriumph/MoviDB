package com.practice.movidb.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.practice.movidb.common.BaseResult
import com.practice.movidb.common.BaseViewModel
import com.practice.movidb.network.common.Result
import com.practice.movidb.shared.domain.details.DetailsUseCase
import com.practice.movidb.shared.domain.details.MovieDetail
import com.practice.movidb.shared.domain.details.MovieDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(private val repository: MovieDetailsRepository) :
    BaseViewModel() {

    val detailModel = MovieDetailModel()

    private val detailsUseCase = DetailsUseCase(
        Dispatchers.IO,
        repository
    )

    fun init(id: Int) {
        viewModelScope.launch {
            detailsUseCase.invoke(id).collect { result ->
                processResponse(result)
            }
        }
    }

    private fun processResponse(result: BaseResult<MovieDetail>) {
        when (result) {
            is Result.Success -> {
                result.data?.let { processDetails(it) }
            }
        }
    }

    private fun processDetails(details: MovieDetail) {
        detailModel.processMovieDetails(details)
    }
}


@Suppress("UNCHECKED_CAST")
class MovieDetailVMFactory(private val repository: MovieDetailsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            return MovieDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewModel: " + modelClass.name)
    }
}