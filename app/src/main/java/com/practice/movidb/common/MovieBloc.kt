package com.practice.movidb.common

import com.practice.movidb.network.common.BaseNetwork
import com.practice.movidb.network.movie.service.MovieService
import javax.inject.Inject

//TODO remove after testing
class MovieBloc @Inject constructor(val movieService: MovieService) {


    fun makeCall(){
        BaseNetwork.makeRequest(movieService)
    }
}