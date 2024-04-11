package com.example.habitstracker.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habitstracker.model.Habit
import com.example.habitstracker.model.HabitsRepository
import java.util.Locale

class HabitsListViewModel: ViewModel() {

    private val model: HabitsRepository = HabitsRepository()

    private val mutableHabits: MutableLiveData<List<Habit>> = MutableLiveData()
    val habits: LiveData<List<Habit>> = mutableHabits


    init {
        mutableHabits.value = model.getAllItems()
    }

    fun sortList()
    {
        mutableHabits.value = model.getAllItems().sortedBy { it.amount }
    }

    fun reverseSortList()
    {
        mutableHabits.value = model.getAllItems().sortedBy { it.amount }.reversed()
    }

    fun removeFilter()
    {
        mutableHabits.value = model.getAllItems()
    }


    fun filterList(query: String?)
    {
        if (query != null) {
            mutableHabits.value = model.getAllItems().filter {
                it.name.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))
            }
        }
    }

    fun removeHabit(position: Int){
        model.removeItem(position)
    }

    fun swapHabits(source: Int, target: Int) {
        model.swapItems(source, target)
    }






}