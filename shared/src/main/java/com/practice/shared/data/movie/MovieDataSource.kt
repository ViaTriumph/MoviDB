package com.practice.shared.data.movie

import com.practice.shared.common.DataMapperUtil
import com.practice.shared.data.db.*

interface MovieDataSource {
    fun getPopularMovies(): List<Movie>

    fun storePopularMovies(list: List<Movie>)

    fun evictPopularMovies()

    fun getNowPlayingMovies(): List<Movie>

    fun storeNowPlayingMovies(list: List<Movie>)

    fun evictNowPlayingMovies()

    fun storeMovies(list: List<Movie>)

    fun getSimilarMovies(id: Int): List<Movie>

    fun insertSimilarMovies(id: Int, list: List<Movie>)
}

class MovieDataSourceImpl(private val appDatabase: AppDatabase) : MovieDataSource {
    private val CACHE_EXPIRY = 7 // 7 days

    override fun getPopularMovies(): List<Movie> {
        return appDatabase
            .popularMoviesDao()
            .getPopularMovies()
            .map { it.convertToDataModel() }
    }

    override fun storePopularMovies(list: List<Movie>) {
        val popularList = list.map { PopularMoviesEntity(0, it.id, System.currentTimeMillis()) }
        appDatabase.popularMoviesDao().storePopularMovieIds(popularList)
    }

    override fun evictPopularMovies() {
        appDatabase.popularMoviesDao().deleteAll()
    }

    override fun getNowPlayingMovies(): List<Movie> {
        return appDatabase
            .nowPlayingMoviesDao()
            .getNowPlayingMovies()
            .map { it.convertToDataModel() }
    }

    override fun storeNowPlayingMovies(list: List<Movie>) {
        val nowPlayingList =
            list.map { NowPlayingMovesEntity(0, it.id, System.currentTimeMillis()) }
        appDatabase.nowPlayingMoviesDao().storeNowPlayingMovies(nowPlayingList)
    }

    override fun evictNowPlayingMovies() {
        appDatabase.nowPlayingMoviesDao().deleteAll()
    }

    override fun storeMovies(list: List<Movie>) {
        val entityList = list.map {
            MovieEntity(
                rowId = 0,
                adult = it.adult,
                genreIds = IntList(DataMapperUtil.convertToNonNull(it.genreIds)),
                id = it.id,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                title = it.title,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
                popularity = it.popularity,
                timeStamp = System.currentTimeMillis()
            )
        }
        appDatabase.movieDao().insertAll(entityList)
    }

    override fun getSimilarMovies(id: Int): List<Movie> {
        val dao = appDatabase.movieDao()
        val similarEntity = dao.getSimilarMovieIds(id)
        return dao.getSimilarMovies(
            similarEntity.similarMovies?.list ?: emptyList()
        )
            .map { it.convertToDataModel() }

    }

    override fun insertSimilarMovies(id: Int, list: List<Movie>) {
        val entity = SimilarMovieEntity(
            0,
            id,
            IntList(list.mapNotNull { it.id }),
            System.currentTimeMillis()
        )
        appDatabase.movieDao().insert(entity)
    }
}