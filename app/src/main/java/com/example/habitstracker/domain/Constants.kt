package com.example.habitstracker.domain

import com.example.habitstracker.domain.converters.HabitTransportConverter


object Constants {
    const val KEY_EXTRA_EDIT_HABIT = "edit_habit"
    const val KEY_HABIT_STATE_GOOD = "Good"
    const val KEY_HABIT_STATE_BAD = "Bad"
    val habitTransportConverter = HabitTransportConverter()
}