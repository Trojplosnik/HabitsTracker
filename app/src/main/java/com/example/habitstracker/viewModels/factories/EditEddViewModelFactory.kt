package com.example.habitstracker.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habitstracker.model.HabitsRepository
import com.example.habitstracker.viewModels.EditEddViewModel

class EditEddViewModelFactory(private val model: HabitsRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditEddViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditEddViewModel(model) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}