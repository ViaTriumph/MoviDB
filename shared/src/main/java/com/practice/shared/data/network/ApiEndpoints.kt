package com.practice.shared.data.network

object ApiEndpoints {

    const val BASE_URL = "https://api.themoviedb.org/3/"

    const val POPULAR_MOVIES = "movie/popular"
    const val NOW_PLAYING_MOVIES = "movie/now_playing"

    const val MOVIE_DETAILS = "movie/{movie_id}"
    const val SIMILAR_MOVIES = "movie/{movie_id}/similar"

    const val SEARCH_MOVIE = "search/movie"
}