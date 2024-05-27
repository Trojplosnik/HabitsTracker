package com.example.habitstracker.data.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class DateTimeConverter {
        private val dateTime = LocalDateTime.of(2024, 5, 1, 0, 0, 0)
        @TypeConverter
        fun currentDateTimeToInt(): Int {
            val now = LocalDateTime.now()
            val secondsSinceLastUpdate= ChronoUnit.SECONDS.between(dateTime, now)
            return secondsSinceLastUpdate.toInt()
        }
    }