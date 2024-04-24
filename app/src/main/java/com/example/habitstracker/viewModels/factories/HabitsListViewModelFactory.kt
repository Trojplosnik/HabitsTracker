package com.example.habitstracker.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habitstracker.model.HabitsRepository
import com.example.habitstracker.viewModels.HabitsListViewModel

class HabitsListViewModelFactory(private val model: HabitsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HabitsListViewModel(model) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
