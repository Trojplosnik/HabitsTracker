package com.example.habitstracker.model

import com.example.habitstracker.Constants
import java.util.Collections

class HabitsRepository{

    private val habits = Constants.habits

    fun getItem(position: Int) = habits[position]


    fun editItem(position: Int, habit: Habit) {
        habits[position] = habit
    }

    fun addItem(habit: Habit) {
        habits.add(habit)
    }

    fun removeItem(position: Int) {
        habits.removeAt(position)
    }

    fun getAllItems() = habits


    fun swapItems(source: Int, target: Int) {
        Collections.swap(habits, source, target)
    }
}