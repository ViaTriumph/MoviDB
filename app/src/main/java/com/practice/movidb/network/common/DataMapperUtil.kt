package com.practice.movidb.network.common

object DataMapperUtil {

    /**
     * Removes nulls from list
     */
    fun <T> removeNullsFromList(list: List<T?>?): List<T>{
        return list?.filterNotNull() ?: listOf()
    }
}