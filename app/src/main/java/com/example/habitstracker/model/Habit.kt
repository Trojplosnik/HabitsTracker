package com.example.habitstracker.model
import android.graphics.Color


data class Habit(
    var name: String = "",
    var description: String = "",
    var amount: String = "",
    var frequency: String = "",
    var type: String = "",
    var priority: String = "",
    var color: Int = Color.GREEN
)
