package com.practice.movidb.network.movie.data

import com.practice.movidb.network.common.BaseRepository
import com.practice.movidb.network.common.Mapper
import com.practice.movidb.network.common.toDomain
import com.practice.movidb.network.movie.domain.MovieRepository
import com.practice.movidb.network.movie.domain.model.PopularMovies as DomainPopularMovies
import com.practice.movidb.network.movie.data.model.PopularMovies as DataPopularMovies
import com.practice.movidb.network.movie.service.MovieService
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class MovieDataRepository @Inject constructor(private val movieService: MovieService) :
    BaseRepository(), MovieRepository {

    override suspend fun getPopularMovies() = getResultFlow {
        movieService.getPopularMovies()
    }.map {
        it.toDomain(object : Mapper<DataPopularMovies, DomainPopularMovies> {
            override fun toDomain(data: DataPopularMovies?): DomainPopularMovies? {
                return data?.convertToDomain()
            }
        })
    }
}