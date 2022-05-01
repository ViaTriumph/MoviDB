package com.practice.movidb.ui.explore

import com.practice.movidb.ui.movie.MovieAdapter
import com.practice.movidb.common.ViewModelComm
import com.practice.movidb.ui.detail.MovieDetailFragment
import com.practice.shared.domain.movie.Movie

//TODO rename
class MovieModel(private val comm: ViewModelComm) {

    val adapter = MovieAdapter(this)

    fun onItemClick(id: Int) {
        comm.launchComponent(MovieDetailFragment.TAG, id)
    }

    fun populateList(list: List<Movie>?) {
        adapter.submitList(convertToPresentation(list))
    }

    private fun convertToPresentation(list: List<Movie>?): List<MovieItemUI> {
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

data class MovieItemUI(
    val id: Int = 0,
    val title: String = "",
    val posterUrl: String = "",
    val genres: String = "",
    val voteAverage: String = ""
)