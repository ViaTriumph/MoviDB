package com.practice.shared.data.search

import com.practice.shared.data.db.AppDatabase
import com.practice.shared.data.db.IntList
import com.practice.shared.data.db.MovieEntity
import com.practice.shared.data.movie.Movie
import javax.inject.Inject

interface SearchDataSource {
    fun getMovieList(query: String): List<Movie>

    fun storeMovieList(list: List<Movie>)
}

class SearchDataSourceImpl @Inject constructor(private val appDatabase: AppDatabase) :
    SearchDataSource {
    override fun getMovieList(query: String): List<Movie> {
        return appDatabase
            .searchFtsDao()
            .getSearchList(query)
            .map { it.convertToDataModel() }
    }

    override fun storeMovieList(list: List<Movie>) {
        val movieEntityList = list.map {
            MovieEntity(
                rowId = 0,
                adult = it.adult,
                genreIds = IntList(com.practice.shared.common.DataMapperUtil.convertToNonNull(it.genreIds)),
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
        appDatabase.movieDao().insertAll(movieEntityList)
    }

}