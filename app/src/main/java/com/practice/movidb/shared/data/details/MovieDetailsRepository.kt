package com.practice.movidb.shared.data.details

import android.util.Log
import com.practice.movidb.common.BaseResult
import com.practice.movidb.network.common.*
import com.practice.movidb.shared.common.DataMapperUtil
import com.practice.movidb.shared.data.movie.MovieDataSource
import com.practice.movidb.shared.domain.details.MovieDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import com.practice.movidb.shared.data.details.MovieDetail as DataMovieDetail
import com.practice.movidb.shared.data.movie.MovieList as DataMovieList
import com.practice.movidb.shared.domain.details.MovieDetail as DomainMovieDetail
import com.practice.movidb.shared.domain.movie.MovieList as DomainMovieList

internal class MovieDetailsRepositoryImpl @Inject constructor(
    private val service: MovieDetailService,
    private val dataSource: MovieDetailDataSource,
    private val movieDataSource: MovieDataSource,
    private val dispatcher: CoroutineDispatcher
) : BaseRepository(), MovieDetailsRepository {

    override fun getMovieDetail(id: Int) = flow {
        emit(Result.Loading())

        val movieDetails = dataSource.getMovieDetails(id)
        val result: BaseResult<DataMovieDetail> = if (movieDetails != null) {
            Result.Success(
                200,
                movieDetails,
                Result.Type.CACHE
            )

        } else {
            val apiResponse: BaseResult<DataMovieDetail> = service.getMovieDetails(id)

            if (apiResponse is Result.Success) {
                val data = apiResponse.data
                if (data != null) {
                    dataSource.insertMovieDetails(data)

                    val cachedData = dataSource.getMovieDetails(id)

                    Result.Success(
                        200,
                        cachedData,
                        Result.Type.CACHE
                    )
                } else {
                    Result.Error(
                        -1,
                        BaseError(message = "null data")
                    ) //TODO define error code for such casese
                }

            } else apiResponse
        }

        emit(
            result.toDomain(object :
                Mapper<DataMovieDetail, DomainMovieDetail> {
                override fun toDomain(data: DataMovieDetail?): DomainMovieDetail? {
                    return data?.convertToDomainModel()
                }
            })
        )
    }
        .catch { e -> Log.e("REPO", e.toString()) }
        .flowOn(dispatcher)

    override fun getSimilarMovies(id: Int) = flow {
        emit(Result.Loading())

        val movieList = movieDataSource.getSimilarMovies(id)
        val result: BaseResult<DataMovieList> = if (!movieList.isNullOrEmpty()) {
            Result.Success(
                200,
                DataMovieList(results = movieList),
                Result.Type.CACHE
            )

        } else {
            val apiResponse: BaseResult<DataMovieList> = service.getSimilarMovies(id)

            if (apiResponse is Result.Success) {
                val list = apiResponse.data?.results
                if (!list.isNullOrEmpty()) {
                    movieDataSource.storeMovies(DataMapperUtil.convertToNonNull(list))
                    movieDataSource.insertSimilarMovies(id, list.filterNotNull())

                    val cachedData = movieDataSource.getSimilarMovies(id)

                    Result.Success(
                        200,
                        DataMovieList(results = cachedData),
                        Result.Type.CACHE
                    )
                } else {
                    Result.Error(
                        -1,
                        BaseError(message = "null data")
                    ) //TODO define error code for such casese
                }

            } else apiResponse
        }

        emit(
            result.toDomain(object :
                Mapper<DataMovieList, DomainMovieList> {
                override fun toDomain(data: DataMovieList?): DomainMovieList? {
                    return data?.convertToDomain()
                }
            })
        )
    }
        .catch { e -> Log.e("REPO", e.toString()) }
        .flowOn(dispatcher)
}