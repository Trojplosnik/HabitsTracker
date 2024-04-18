package com.example.habitstracker.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.model.Habit
import com.example.habitstracker.model.HabitDatabase
import com.example.habitstracker.model.HabitsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitsListViewModel(application: Application): AndroidViewModel(application) {

    private val model: HabitsRepository = HabitsRepository(HabitDatabase.getDatabase(application).habitDao())



    var habits: LiveData<List<Habit>> = model.getAllHabits()



    fun searchDatabase(query: String?)
    {
        if (query != null) {
            habits = model.searchDatabase(query)
        }
    }

    fun sortDatabase(isAsc: Boolean = false)
    {
        habits = if (isAsc)
            model.sortDatabaseASC()
        else
            model.sortDatabaseDESC()
    }



    fun deleteHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
                model.deleteHabit(habit)
        }
    }



    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            model.deleteAll()
        }
    }



}