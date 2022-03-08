package com.practice.movidb.network.search.data

import com.practice.movidb.network.common.BaseRepository
import com.practice.movidb.network.common.Mapper
import com.practice.movidb.network.common.toDomain
import com.practice.movidb.network.search.domain.SearchRepository
import com.practice.movidb.network.search.domain.model.SearchResponse as DomainSearchResponse
import com.practice.movidb.network.search.data.model.SearchResponse as DataSearchResponse
import com.practice.movidb.network.search.service.SearchService
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class SearchDataRepository @Inject constructor(private val searchService: SearchService): BaseRepository(), SearchRepository {

    override suspend fun getSearchResults(query: String) = flow { emit(Result.success(com.practice.movidb.network.search.domain.model.SearchResponse(
        0,
        emptyList(),
        0,
        0
    ))) }


//        getResultFlow {
//        searchService.getSearchResult(query)
//    }.map {
//        it.toDomain(object : Mapper<DataSearchResponse, DomainSearchResponse>{
//            override fun toDomain(data: DataSearchResponse?): DomainSearchResponse? {
//                return data?.convertToDomain()
//            }
//        })
//    }
}