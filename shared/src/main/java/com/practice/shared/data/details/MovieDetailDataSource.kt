package com.practice.shared.data.details

import com.practice.shared.data.db.AppDatabase
import com.practice.shared.data.db.MovieDetailEntity

interface MovieDetailDataSource {

    fun getMovieDetails(id: Int): MovieDetail?

    fun insertMovieDetails(details: MovieDetail)
}

class MovieDetailDataSourceImpl(private val appDatabase: AppDatabase) : MovieDetailDataSource {

    override fun getMovieDetails(id: Int): MovieDetail? {
        return appDatabase
            .movieDetailDao()
            .getMovieDetails(id)
            ?.convertToDataModel()
    }

    override fun insertMovieDetails(details: MovieDetail) {
        val entity = MovieDetailEntity(
            rowId = 0,
            adult = details.adult,
            backdropPath = details.backdropPath,
            budget = details.budget,
            genres = details.genres,
            homepage = details.homepage,
            id = details.id,
            imdbId = details.imdbId,
            originalLanguage = details.originalLanguage,
            originalTitle = details.originalTitle,
            overview = details.overview,
            popularity = details.popularity,
            posterPath = details.posterPath,
            releaseDate = details.releaseDate,
            revenue = details.revenue,
            runtime = details.runtime,
            status = details.status,
            tagline = details.tagline,
            title = details.title,
            video = details.video,
            voteAverage = details.voteAverage,
            voteCount = details.voteCount,
        )
        appDatabase.movieDetailDao().insertMovieDetail(entity)
    }

}