package com.practice.movidb.shared.data.search.datasource

import com.practice.model.movie.Movie
import com.practice.movidb.shared.data.db.AppDatabase
import com.practice.movidb.shared.data.db.MovieEntity
import javax.inject.Inject

interface SearchDataSource {
    fun getMovieList(): List<Movie>

    fun storeMovieList(list: List<Movie>)
}

class SearchDataSourceImpl @Inject constructor(private val appDatabase: AppDatabase) :
    SearchDataSource {
    override fun getMovieList(): List<Movie> {
        return appDatabase
            .searchFtsDao()
            .getSearchList()
            .map { it.convertToDomainModel() }
    }

    override fun storeMovieList(list: List<Movie>) {
        val movieEntityList = list.map {
            MovieEntity(
                rowId = 0,
                adult = it.adult,
                genreIds = it.genreIds,
                id = it.id,
                overview = it.overview,
                posterPath = it.posterPath,
                releaseDate = it.releaseDate,
                title = it.title,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount,
            )
        }
        appDatabase.searchFtsDao().insertAll(movieEntityList)
    }

}