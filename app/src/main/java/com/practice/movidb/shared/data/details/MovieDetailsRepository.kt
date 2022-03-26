package com.practice.movidb.shared.data.details

import android.util.Log
import com.practice.movidb.common.BaseResult
import com.practice.movidb.network.common.*
import com.practice.movidb.shared.domain.details.MovieDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import com.practice.movidb.shared.data.details.MovieDetail as DataMovieDetail
import com.practice.movidb.shared.domain.details.MovieDetail as DomainMovieDetail

internal class MovieDetailsRepositoryImpl @Inject constructor(
    private val movieDetailService: MovieDetailService,
    private val movieDetailDataSource: MovieDetailDataSource,
    private val coroutineDispatcher: CoroutineDispatcher
) : BaseRepository(), MovieDetailsRepository {

    override fun getMovieDetail(id: Int) = flow {
        emit(Result.Loading())

        val movieDetails = movieDetailDataSource.getMovieDetails(id)
        val result: BaseResult<DataMovieDetail> = if (movieDetails != null) {
            Result.Success(
                200,
                movieDetails,
                Result.Type.CACHE
            )

        } else {
            val apiResponse: BaseResult<DataMovieDetail> = getResult {
                movieDetailService.getMovieDetails(id)
            }

            if (apiResponse is Result.Success) {
                val data = apiResponse.data
                if (data != null) {
                    movieDetailDataSource.insertMovieDetails(data)

                    val cachedData = movieDetailDataSource.getMovieDetails(id)

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
        .flowOn(coroutineDispatcher)
}