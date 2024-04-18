package com.example.habitstracker.viewModels


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope

import com.example.habitstracker.model.Habit
import com.example.habitstracker.model.HabitDatabase
import com.example.habitstracker.model.HabitsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EditEddViewModel(application: Application) : AndroidViewModel(application) {

    private val model: HabitsRepository = HabitsRepository(HabitDatabase.getDatabase(application).habitDao())


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