package com.practice.movidb.network.search.data

import com.practice.movidb.common.BaseResult
import com.practice.movidb.network.common.BaseRepository
import com.practice.movidb.network.common.Mapper
import com.practice.movidb.network.common.Result
import com.practice.movidb.network.common.toDomain
import com.practice.movidb.network.search.domain.SearchRepository
import com.practice.movidb.network.search.service.SearchService
import com.practice.movidb.shared.common.DataMapperUtil
import com.practice.movidb.shared.data.search.datasource.SearchDataSource
import com.practice.movidb.shared.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import com.practice.movidb.shared.data.movie.MovieList as DataMovieList
import com.practice.movidb.shared.domain.movie.MovieList as DomainMovieList

internal class SearchDataRepository @Inject constructor(
    private val searchService: SearchService,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val dataSource: SearchDataSource
) : BaseRepository(), SearchRepository {

    override fun getSearchResults(queryOptions: Map<String, String>) = flow {
        emit(Result.Loading())

        //todo pagination
        val movieList = dataSource.getMovieList()
        val result: BaseResult<DataMovieList> = if (!movieList.isNullOrEmpty()) {
            val cachedData = DataMovieList(
                results = movieList
            )
            Result.Success(
                200,
                cachedData,
                Result.Type.CACHE
            )

        } else {
            val apiResponse: BaseResult<DataMovieList> = getResult {
                searchService.getSearchResult(queryOptions)
            }

            if (apiResponse is Result.Success) {
                val list = apiResponse.data?.results
                dataSource.storeMovieList(DataMapperUtil.convertToNonNull(list))

                val cachedData = DataMovieList(
                    results = dataSource.getMovieList()
                )

                Result.Success(
                    200,
                    cachedData,
                    Result.Type.CACHE
                )

            } else {
                apiResponse
            }
        }

        emit(
            result.toDomain(object : Mapper<DataMovieList, DomainMovieList> {
                override fun toDomain(data: DataMovieList?): DomainMovieList? {
                    return data?.convertToDomain()
                }
            })
        )
    }.flowOn(coroutineDispatcher)
}