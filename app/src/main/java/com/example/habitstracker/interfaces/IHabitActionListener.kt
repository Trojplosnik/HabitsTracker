package com.example.habitstracker.interfaces

import com.example.habitstracker.model.Habit


interface IHabitActionListener {
    fun onClick(habit: Habit)
}