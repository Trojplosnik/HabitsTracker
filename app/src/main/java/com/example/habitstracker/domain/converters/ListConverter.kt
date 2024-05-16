package com.example.habitstracker.domain.converters

import androidx.room.TypeConverter

class ListConverter {
    @TypeConverter
    fun fromList(list: List<Int>): String {
        return list.joinToString { "," }
    }

    @TypeConverter
    fun toList(str: String): List<Int> {
        return str.split(",").filter { it.isNotBlank() }.map { it.toInt() }
    }
}