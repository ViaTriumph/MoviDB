package com.practice.movidb.network.movie.data

import com.practice.movidb.network.common.BaseRepository
import com.practice.movidb.network.common.Mapper
import com.practice.movidb.network.common.Result
import com.practice.movidb.network.common.toDomain
import com.practice.movidb.network.movie.domain.MovieRepository
import com.practice.movidb.network.movie.domain.model.MovieList
import com.practice.movidb.network.movie.service.MovieService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.practice.movidb.network.movie.data.model.MovieList as DataMovieList

internal class MovieDataRepository @Inject constructor(private val movieService: MovieService) :
    BaseRepository(), MovieRepository {

    override fun getPopularMovies() = flow {
        emit(Result.Loading())
        emit(getResultFlow {
            movieService.getPopularMovies()
        }.toDomain(object : Mapper<DataMovieList, MovieList> {
            override fun toDomain(data: DataMovieList?): MovieList? {
                return data?.convertToDomain()
            }
        }))
    }

    override fun getNowPlayingMovies() = flow {
        emit(Result.Loading())
        emit(getResultFlow {
            movieService.getNowPlayingMovies()
        }.toDomain(object : Mapper<DataMovieList, MovieList> {
            override fun toDomain(data: DataMovieList?): MovieList? {
                return data?.convertToDomain()
            }
        }))
    }
}