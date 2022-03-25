package com.practice.movidb.shared.domain.search

import com.practice.movidb.common.BaseResult
import com.practice.movidb.network.search.domain.SearchRepository
import com.practice.movidb.shared.di.IODispatcher
import com.practice.movidb.shared.domain.FlowUseCase
import com.practice.movidb.shared.domain.movie.MovieList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

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

class SearchUseCase(
    private val repository: SearchRepository,
    @IODispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<SearchUseCaseParams, MovieList>(dispatcher) {
    override fun execute(parameters: SearchUseCaseParams): Flow<BaseResult<MovieList>> {
        val sanitizedParams = parameters.copy(query = sanitizeSearchQuery(parameters.query))

        return repository.getSearchResults(sanitizedParams.getQueryMap()) // TODO Add filters if necessary
    }

    private fun sanitizeSearchQuery(query: String): String {
        val modifiedQuery = query.replace(Regex.fromLiteral("\""), "\"\"")
        return String.format("*%s*", modifiedQuery)
    }
}