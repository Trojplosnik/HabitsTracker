package com.example.habitstracker.presentation.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.domain.entities.Frequency
import com.example.habitstracker.domain.entities.Habit
import com.example.habitstracker.domain.entities.Priority
import com.example.habitstracker.domain.entities.Type
import com.example.habitstracker.domain.usecases.AddHabitUseCase
import com.example.habitstracker.domain.usecases.GetHabitUseCase
import com.example.habitstracker.domain.usecases.UpdateHabitUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditEddViewModel @Inject constructor(
    private val getHabitUseCase: GetHabitUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val addHabitUseCase: AddHabitUseCase
) : ViewModel() {


    data class EditEddUiState(
        val showToast: Boolean = false,
        val isSaving: Boolean = false,
        val habitLoaded: Boolean = false,
        val changeColor: Boolean = false
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
        currentHabit = if (newAmount.isNotBlank())
            currentHabit.copy(amount = newAmount.toInt())
        else
            currentHabit.copy(amount = -1)
    }

    fun setFrequency(newFrequency: Frequency) {
        currentHabit = currentHabit.copy(
            frequency = newFrequency
        )
    }

    fun setPriority(newPriority: Priority) {
        currentHabit = currentHabit.copy(priority = newPriority)
    }

    fun setType(newType: Type) {
        currentHabit = currentHabit.copy(type = newType)
    }

    fun setColor(newColor: Int) {
        currentHabit = currentHabit.copy(color = newColor)
        _uiState.update { state ->
            state.copy(changeColor = true)
        }
    }


    fun getHabitById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            currentHabit = getHabitUseCase(id)
            _uiState.update { state ->
                state.copy(habitLoaded = true)
            }
        }
    }

    private fun checkInputIsEmpty() = currentHabit.name.isEmpty() ||
            currentHabit.amount < 0 ||
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
                    updateHabitUseCase(currentHabit)
                } else
                    addHabitUseCase(currentHabit)
            }
        }
    }

    fun dismissState() {
        _uiState.update { state ->
            state.copy(
                showToast = false,
                isSaving = false,
                habitLoaded = false,
                changeColor = false
            )
        }
    }

}

