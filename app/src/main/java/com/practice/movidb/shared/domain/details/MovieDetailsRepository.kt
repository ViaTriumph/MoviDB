package com.practice.movidb.shared.domain.details

import com.practice.movidb.common.BaseResult
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {

    fun getMovieDetail(id: Int): Flow<BaseResult<MovieDetail>>
}