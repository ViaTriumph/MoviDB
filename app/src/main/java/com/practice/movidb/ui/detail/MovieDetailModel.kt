package com.practice.movidb.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.practice.movidb.shared.domain.details.MovieDetail
import com.practice.movidb.ui.UIUtils

/**
 * image
 * year, genre, duration
 * rating
 * description
 *
 * trailer
 */
class MovieDetailModel {

    private val _uiData = MutableLiveData(MovieDetailUI())
    val uiData: LiveData<MovieDetailUI> = _uiData

    fun processMovieDetails(details: MovieDetail) {
        val uiData = MovieDetailUI()
        uiData.backdropImage = "https://image.tmdb.org/t/p/original" + details.posterPath
        uiData.title = details.title
        val year = UIUtils.parseDateToYear(details.releaseDate)
        val genres = details.genres.joinToString("/") { it.name }
        val duration = "${(details.runtime / 60)}h ${(details.runtime % 60)}m"
        uiData.meta = "$year · $genres · $duration"
        uiData.rating = details.voteAverage.toFloat() * (0.5f)
        uiData.description = details.overview

        uiData.state = "READY"

        _uiData.value = uiData
    }

    //TODO
    fun onDescriptionToggle() {

    }
}

data class MovieDetailUI(
    var state: String = "LOADING",
    var backdropImage: String = "",
    var title: String = "",
    var meta: String = "",
    var rating: Float = 0f,
    var description: String = ""
)