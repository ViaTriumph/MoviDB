package com.practice.movidb.network.search.data

import com.practice.movidb.common.BaseResult
import com.practice.movidb.network.common.BaseRepository
import com.practice.movidb.network.common.Mapper
import com.practice.movidb.network.common.Result
import com.practice.movidb.network.common.toDomain
import com.practice.movidb.network.search.data.model.SearchResponse
import com.practice.movidb.network.search.domain.SearchRepository
import com.practice.movidb.network.search.domain.model.SearchMovieList
import com.practice.movidb.network.search.service.SearchService
import com.practice.movidb.shared.data.search.datasource.SearchDataSource
import com.practice.movidb.shared.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class SearchDataRepository @Inject constructor(
    private val searchService: SearchService,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val dataSource: SearchDataSource
) : BaseRepository(), SearchRepository {

    override fun getSearchResults(queryOptions: Map<String, String>) = flow {
        emit(Result.Loading())
        val response: BaseResult<SearchMovieList> = getResultFlow {
            searchService.getSearchResult(queryOptions)
        }.toDomain(object : Mapper<SearchResponse, SearchMovieList> {
            override fun toDomain(data: SearchResponse?): SearchMovieList? {
                return data?.convertToDomain()
            }
        })
        emit(response)
    }.flowOn(coroutineDispatcher)
}