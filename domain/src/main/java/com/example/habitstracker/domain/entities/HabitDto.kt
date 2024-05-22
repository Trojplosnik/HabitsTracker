package com.example.habitstracker.domain.entities

import androidx.room.TypeConverters
import com.example.habitstracker.domain.converters.ListConverter
import com.google.gson.annotations.SerializedName

@TypeConverters(ListConverter::class)
class HabitDto (
    @SerializedName("count")
    val count: Int,
    @SerializedName("date")
    val date: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("frequency")
    val frequency: Int,
    @SerializedName("priority")
    val priority: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("color")
    val color: Int,
    @SerializedName("done_dates")
    val doneDates: List<Int>
)