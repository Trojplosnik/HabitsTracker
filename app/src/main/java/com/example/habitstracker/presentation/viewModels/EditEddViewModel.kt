package com.example.habitstracker.presentation.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.habitstracker.domain.entities.Habit
import com.example.habitstracker.domain.entities.Priority
import com.example.habitstracker.domain.entities.Type
import com.example.habitstracker.domain.repositorys.IHabitsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


class EditEddViewModel (private val model: IHabitsRepository) : ViewModel() {
    data class EditEddUiState(
        val showToast: Boolean = false,
        val isSaving: Boolean = false,
        val habitLoaded: Boolean = false
    )


    private val _uiState = MutableStateFlow(EditEddUiState())
    val uiState: StateFlow<EditEddUiState>
        get() = _uiState.asStateFlow()


    private var currentHabit: Habit = Habit()

    fun getCurrentHabit() = currentHabit


    fun setName(newName: String) {
        currentHabit = currentHabit.copy(name = newName)
    }

    fun setDescription(newDescription: String) {
        currentHabit = currentHabit.copy(description = newDescription)
    }

    fun setAmount(newAmount: String) {
        currentHabit = currentHabit.copy(amount = newAmount.toInt())
    }

    fun setFrequency(newFrequency: String) {
        currentHabit = currentHabit.copy(frequency = newFrequency.toInt())
    }

    fun setPriority(newPriority: Priority) {
        currentHabit = currentHabit.copy(priority = newPriority)
    }

    fun setType(newType: Type) {
        currentHabit = currentHabit.copy(type = newType)
    }

    fun setColor(newColor: Int) {
        currentHabit = currentHabit.copy(color = newColor)
    }


    fun getHabitById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            currentHabit = model.getHabitById(id)
            _uiState.update { state ->
                state.copy(habitLoaded = true)
            }
        }
    }

    private fun checkInputIsEmpty() = currentHabit.name.isEmpty() ||
                                        currentHabit.name.isEmpty() ||
                                        currentHabit.name.isEmpty() ||
                                        currentHabit.type == Type.NOT_SELECTED



    fun sendToDataBase() {
        if (checkInputIsEmpty()) {
            _uiState.update { state ->
                state.copy(isSaving = true, showToast = true)
            }
        } else {
            _uiState.update { state ->
                state.copy(isSaving = true)
            }
            viewModelScope.launch(Dispatchers.IO) {
                if (currentHabit.id != 0) {
                    model.updateHabit(currentHabit)
                } else
                    model.addHabit(currentHabit)
            }
        }
    }

    fun dismissState() {
        _uiState.update { state ->
            state.copy()
        }
    }

}

