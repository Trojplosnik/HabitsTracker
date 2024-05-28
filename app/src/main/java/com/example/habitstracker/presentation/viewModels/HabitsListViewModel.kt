package com.example.habitstracker.presentation.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitstracker.domain.entities.Habit
import com.example.habitstracker.domain.entities.Type
import com.example.habitstracker.domain.usecases.DeleteHabitUseCase
import com.example.habitstracker.domain.usecases.DoneHabitUseCase
import com.example.habitstracker.domain.usecases.GetAllHabitsUseCase
import com.example.habitstracker.domain.usecases.SearchHabitsUseCase
import com.example.habitstracker.domain.usecases.SortHabitsUseCase
import com.example.habitstracker.domain.usecases.SynchronizeWithRemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class HabitsListViewModel @Inject constructor(
    private val doneHabitUseCase: DoneHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val sortHabitsUseCase: SortHabitsUseCase,
    private val searchHabitsUseCase: SearchHabitsUseCase,
    private val getAllHabitsUseCase: GetAllHabitsUseCase,
    private val synchronizeWithRemoteUseCase: SynchronizeWithRemoteUseCase
) :
    ViewModel() {

    private val _habits: MutableLiveData<List<Habit>> = MutableLiveData(listOf())
    val habits: LiveData<List<Habit>> = _habits

    enum class ToastState {
        GOOD_LESS, BAD_LESS, GOOD_MORE, BAD_MORE
    }

    data class HabitsListUiState(
        val showToast: Boolean = false,
        val toastState: ToastState = ToastState.GOOD_MORE,
        val leftToDo: Int = 0
    )

    private val _uiState = MutableStateFlow(HabitsListUiState())
    val uiState: StateFlow<HabitsListUiState>
        get() = _uiState.asStateFlow()


    init {
        setHabits()
        viewModelScope.launch(Dispatchers.IO) {
            synchronizeWithRemoteUseCase()
        }
    }


    fun setHabits() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllHabitsUseCase().collect { habitsList ->
                _habits.postValue(habitsList)
            }
        }
    }


    fun searchDatabase(query: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (query != null) {
                _habits.postValue(searchHabitsUseCase(query))
            }
        }
    }

    fun sortDatabase(isDesc: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _habits.value = sortHabitsUseCase(isDesc)
            }
        }

    }


    fun deleteHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteHabitUseCase(habit)
        }
    }

    fun doneHabit(habit: Habit) {
        val leftToDo = habit.amount - habit.doneDates.size - 1
        if (leftToDo > 0) {
            Log.d("time", "${habit.doneDates} , ${habit.id}")
            when (habit.type) {
                Type.GOOD -> _uiState.update { state ->
                    state.copy(
                        toastState = ToastState.GOOD_LESS,
                        leftToDo = leftToDo,
                        showToast = true
                    )
                }

                Type.BAD -> _uiState.update { state ->
                    state.copy(
                        toastState = ToastState.BAD_LESS,
                        leftToDo = leftToDo,
                        showToast = true
                    )
                }

                Type.NOT_SELECTED -> throw IllegalStateException("Type of habit is not selected")
            }
        } else {
            when (habit.type) {
                Type.GOOD -> _uiState.update { state ->
                    state.copy(toastState = ToastState.GOOD_MORE, showToast = true)
                }

                Type.BAD -> _uiState.update { state ->
                    state.copy(toastState = ToastState.BAD_MORE, showToast = true)
                }

                Type.NOT_SELECTED -> throw IllegalStateException("Type of habit is not selected")
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            doneHabitUseCase(habit)
        }
    }

    fun dismissState() {
        _uiState.update { state ->
            state.copy(showToast = false, leftToDo =0)
        }
    }

}