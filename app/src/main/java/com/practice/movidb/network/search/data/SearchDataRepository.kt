package com.practice.movidb.network.search.data

import android.util.Log
import com.practice.movidb.common.BaseResult
import com.practice.movidb.network.common.BaseRepository
import com.practice.movidb.network.common.Mapper
import com.practice.movidb.network.common.Result
import com.practice.movidb.network.common.toDomain
import com.practice.movidb.network.search.domain.SearchRepository
import com.practice.movidb.network.search.service.SearchService
import com.practice.movidb.shared.common.DataMapperUtil
import com.practice.movidb.shared.data.search.SearchDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import com.practice.movidb.shared.data.movie.MovieList as DataMovieList
import com.practice.movidb.shared.domain.movie.MovieList as DomainMovieList

internal class SearchDataRepository @Inject constructor(
    private val searchService: SearchService,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val dataSource: SearchDataSource
) : BaseRepository(), SearchRepository {

    override fun getSearchResults(queryOptions: Map<String, String>) = flow {
        emit(Result.Loading())
        val query = queryOptions["query"] ?: return@flow
        var movieList = dataSource.getMovieList(sanitizeSearchQuery(query))
        movieList = if (movieList.size > 10) movieList else emptyList()

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
                var list = apiResponse.data?.results
                dataSource.storeMovieList(DataMapperUtil.convertToNonNull(list))

                list = dataSource.getMovieList(sanitizeSearchQuery(query))
                list = if (list.isEmpty()) apiResponse.data?.results else list

                val cachedData = DataMovieList(
                    results = list
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
    }
        .catch { e -> Log.e("REPO", e.toString()) }
        .flowOn(coroutineDispatcher)
}


private fun sanitizeSearchQuery(query: String): String {
    val modifiedQuery = query.replace(Regex.fromLiteral("\""), "\"\"")
    return String.format("*%s*", modifiedQuery)
}