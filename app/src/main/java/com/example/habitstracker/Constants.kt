package com.example.habitstracker

import com.example.habitstracker.model.Habit

object Constants {
    const val KEY_EXTRA_EDIT_HABIT = "edit_habit"
    const val KEY_EXTRA_TYPE = "Type"
    const val KEY_HABIT_STATE_GOOD = "Good"
    const val KEY_HABIT_STATE_BAD = "Bad"
    val habits = mutableListOf<Habit>()

}