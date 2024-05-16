package com.example.habitstracker.presentation.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habitstracker.data.repositories.HabitsRepositoryImpl
import com.example.habitstracker.presentation.viewModels.EditEddViewModel

class EditEddViewModelFactory(private val model: HabitsRepositoryImpl): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditEddViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditEddViewModel(model) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}