package com.example.habitstracker.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.habitstracker.model.Habit
import com.example.habitstracker.model.HabitsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EditEddViewModel(private val model: HabitsRepository) : ViewModel() {


    var currentHabitId: Int = 0




    fun addEditHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            if (currentHabitId != 0) {
                model.updateHabit(habit)
            } else
                model.addHabit(habit)
        }
    }


}