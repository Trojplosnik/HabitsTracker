package com.example.habitstracker.data.remote.model

import com.google.gson.annotations.SerializedName

class HabitDto (
    @SerializedName("count")
    var count: Int = 0,
    @SerializedName("date")
    var date: Int = 0,
    @SerializedName("description")
    var description: String = "",
    @SerializedName("frequency")
    var frequency: Int = 0,
    @SerializedName("priority")
    var priority: Int = 0,
    @SerializedName("title")
    var title: String = "",
    @SerializedName("type")
    var type: Int = 0,
    @SerializedName("uid")
    var uid: String = "",
    @SerializedName("done_dates")
    var doneDates: List<Int> = emptyList()
)