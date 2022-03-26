package com.practice.movidb.ui.explore

import com.practice.movidb.common.ViewModelComm
import com.practice.movidb.ui.detail.MovieDetailFragment

//TODO rename
class MovieModel(private val comm: ViewModelComm) {

    fun onItemClick(id: Int) {
        comm.launchComponent(MovieDetailFragment.TAG, id)
    }
}