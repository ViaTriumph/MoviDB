package com.practice.movidb.ui

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
}