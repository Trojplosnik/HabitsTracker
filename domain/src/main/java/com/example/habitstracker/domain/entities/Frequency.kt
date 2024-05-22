package com.example.habitstracker.domain.entities

import java.util.Locale

enum class Frequency(val timeInSeconds: Int) {
    DAY(86400),
    WEEK(604800),
    MONTH(2592000),
    YEAR(31536000);


    fun getFrequency(): String = name.lowercase(Locale.ROOT)
}