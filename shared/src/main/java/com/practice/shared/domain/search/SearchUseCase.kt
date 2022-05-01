package com.practice.shared.domain.search

import com.practice.shared.data.network.BaseResult
import com.practice.shared.di.IODispatcher
import com.practice.shared.domain.FlowUseCase
import com.practice.shared.domain.movie.MovieList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class SearchUseCaseParams(
    val language: String?,
    val query: String,
    val page: Int,
    val adult: Boolean?,
    val region: String?,
    val releaseYear: Int?
) {
    fun getQueryMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map["query"] = query
        map["page"] = page.toString()
        language?.let { map["language"] = it }
        adult?.let { map["include_adult"] = it.toString() }
        region?.let { map["region"] = it }
        releaseYear?.let { map["primary_release_year"] = it.toString() }

        return map
    }
}

class SearchUseCase @Inject constructor(
    private val repository: SearchRepository,
    @IODispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<SearchUseCaseParams, MovieList>(dispatcher) {
    override fun execute(parameters: SearchUseCaseParams): Flow<BaseResult<MovieList>> {
        return repository.getSearchResults(parameters.getQueryMap()) // TODO Add filters if necessary
    }
}