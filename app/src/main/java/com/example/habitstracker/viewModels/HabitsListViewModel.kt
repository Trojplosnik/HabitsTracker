package com.example.habitstracker.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.model.Habit
import com.example.habitstracker.model.HabitsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HabitsListViewModel(private val model: HabitsRepository) : ViewModel() {

    private val _habits: MutableLiveData<List<Habit>> = MutableLiveData(listOf())
    val habits: LiveData<List<Habit>> = _habits


    init {
        setHabits()
    }

    fun setHabits() {
        viewModelScope.launch(Dispatchers.IO) {
            model.getAllHabits().collect { habitsList ->
                _habits.postValue(habitsList)
            }
        }
    }


    fun searchDatabase(query: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (query != null) {
                _habits.postValue(model.searchDatabase(query))
            }
        }
    }

    fun sortDatabase(isDesc: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            val sortedHabits = if (isDesc)
                model.sortDatabaseDESC()
            else
                model.sortDatabaseASC()

            withContext(Dispatchers.Main) {
                _habits.value = sortedHabits
            }
        }

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