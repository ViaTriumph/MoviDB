package com.practice.movidb.ui

import com.practice.movidb.ui.explore.MovieItemUI
import com.practice.shared.domain.movie.Movie
import java.text.SimpleDateFormat
import java.util.*

object UIUtils {

    /**
     * yyyy-MM-dd format
     */
    fun parseDateToYear(date: String): String {
        val inFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val outFormat = SimpleDateFormat("yyyy", Locale.ENGLISH)

        val inDate = inFormat.parse(date) ?: return "-"

        return outFormat.format(inDate)
    }

    fun convertToPresentation(list: List<Movie>?): List<MovieItemUI> {
        if (list == null) return emptyList()
        return list.map {
            MovieItemUI(
                id = it.id,
                title = it.title,
                posterUrl = it.posterPath
            )
        }
    }
}