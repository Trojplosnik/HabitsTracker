package com.example.habitstracker


import android.graphics.Color
import java.io.Serializable


data class Habit(
    var name: String = "",
    var description: String = "",
    var amount: String = "",
    var frequency: String = "",
    var type: String = "",
    var priority: String = "",
    var color: Int = Color.GREEN
)  : Serializable
