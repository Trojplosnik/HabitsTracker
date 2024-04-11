package com.example.habitstracker.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.habitstracker.model.Habit
import com.example.habitstracker.model.HabitsRepository


class EditEddViewModel: ViewModel() {

    private val model: HabitsRepository = HabitsRepository()


    private val mutablePos: MutableLiveData<Int> = MutableLiveData()

    val mutableEditHabit: MutableLiveData<Boolean> = MutableLiveData(false)



    private var mutableHabit = MutableLiveData(Habit())
    val habit: LiveData<Habit> = mutableHabit




    fun setHabit(position: Int) {
        mutablePos.value = position
        mutableEditHabit.value = true
    }


    fun getEditHabit() = model.getItem(mutablePos.value!!)


    fun addEditHabit(habit: Habit){
        if (mutableEditHabit.value == true) {
            model.editItem(mutablePos.value!!, habit)
        }
        else
            model.addItem(habit)
    }





}