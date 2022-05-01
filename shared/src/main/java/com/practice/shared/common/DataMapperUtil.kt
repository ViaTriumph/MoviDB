package com.practice.shared.common

object DataMapperUtil {

    fun <T> convertToNonNull(list: List<T?>?): List<T> {
        return list?.filterNotNull() ?: emptyList()
    }
}