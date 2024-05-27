package com.example.habitstracker.data.remote.entities

import com.google.gson.annotations.SerializedName

data class DoneHabit(
    @SerializedName("date") val date: Int,
    @SerializedName("habit_uid") val uid: String
)
