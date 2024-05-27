package com.example.habitstracker.data.converters


import androidx.room.TypeConverter

class ListConverter {
    @TypeConverter
    fun fromList(list: List<Int>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toList(str: String): MutableList<Int> {
        return str.split(",").mapNotNull { it.toIntOrNull() }.toMutableList()
    }
}
