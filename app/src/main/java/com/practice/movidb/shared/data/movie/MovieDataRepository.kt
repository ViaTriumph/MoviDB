package com.practice.movidb.shared.data.movie

import com.practice.movidb.common.BaseResult
import com.practice.movidb.network.common.BaseRepository
import com.practice.movidb.network.common.Mapper
import com.practice.movidb.network.common.Result
import com.practice.movidb.network.common.toDomain
import com.practice.movidb.network.movie.domain.MovieRepository
import com.practice.movidb.network.movie.service.MovieService
import com.practice.movidb.shared.common.DataMapperUtil
import com.practice.movidb.shared.di.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import com.practice.movidb.shared.data.movie.MovieList as DataMovieList
import com.practice.movidb.shared.domain.movie.MovieList as DomainMovieList

internal class MovieDataRepository @Inject constructor(
    private val movieService: MovieService,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val movieDataSource: MovieDataSource
) :
    BaseRepository(), MovieRepository {

    override fun getPopularMovies() = flow {
        emit(Result.Loading())


        val movieList = movieDataSource.getPopularMovies()
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
                movieService.getPopularMovies()
            }

            if (apiResponse is Result.Success) {
                val list = DataMapperUtil.convertToNonNull(apiResponse.data?.results)
                movieDataSource.storeMovies(list)
                movieDataSource.storePopularMovies(list)

                val cachedData = DataMovieList(
                    results = movieDataSource.getPopularMovies()
                )


                Result.Success(
                    200,
                    cachedData,
                    Result.Type.CACHE
                )

            } else apiResponse
        }

        emit(
            result.toDomain(object : Mapper<DataMovieList, DomainMovieList> {
                override fun toDomain(data: DataMovieList?): DomainMovieList? {
                    return data?.convertToDomain()
                }
            })
        )
    }.flowOn(coroutineDispatcher)

    override fun getNowPlayingMovies() = flow {
        emit(Result.Loading())


        val movieList = movieDataSource.getNowPlayingMovies()
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
                movieService.getNowPlayingMovies()
            }

            if (apiResponse is Result.Success) {
                val list = DataMapperUtil.convertToNonNull(apiResponse.data?.results)
                movieDataSource.storeMovies(list)
                movieDataSource.storeNowPlayingMovies(list)

                val cachedData = DataMovieList(
                    results = movieDataSource.getNowPlayingMovies()
                )

                Result.Success(
                    200,
                    cachedData,
                    Result.Type.CACHE
                )

            } else apiResponse
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