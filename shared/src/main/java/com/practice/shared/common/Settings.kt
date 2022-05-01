package com.practice.shared.common

object Settings {

    private var region = "en-gb"
    private var adult = false
    private var language = ""

    fun getRegion() = region
    fun getAdult() = adult
    fun getLanguage() = language

    fun setRegion(value: String) {
        region = value
    }

    fun setAdult(value: Boolean) {
        adult = value
    }

    fun setLanguage(value: String) {
        language = value
    }


}