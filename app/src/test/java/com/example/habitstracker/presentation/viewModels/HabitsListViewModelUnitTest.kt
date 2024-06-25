package com.example.habitstracker.presentation.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.habitstracker.domain.Constants
import com.example.habitstracker.domain.entities.Frequency
import com.example.habitstracker.domain.entities.Habit
import com.example.habitstracker.domain.entities.Priority
import com.example.habitstracker.domain.entities.Type
import com.example.habitstracker.domain.usecases.DeleteHabitUseCase
import com.example.habitstracker.domain.usecases.DoneHabitUseCase
import com.example.habitstracker.domain.usecases.GetAllHabitsUseCase
import com.example.habitstracker.domain.usecases.SearchHabitsUseCase
import com.example.habitstracker.domain.usecases.SortHabitsUseCase
import com.example.habitstracker.domain.usecases.SynchronizeWithRemoteUseCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class HabitsListViewModelUnitTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val doneHabitUseCase: DoneHabitUseCase = Mockito.mock()
    private val deleteHabitUseCase: DeleteHabitUseCase = Mockito.mock()
    private val sortHabitsUseCase: SortHabitsUseCase = Mockito.mock()
    private val searchHabitsUseCase: SearchHabitsUseCase = Mockito.mock()
    private val getAllHabitsUseCase: GetAllHabitsUseCase = Mockito.mock()
    private val synchronizeWithRemoteUseCase: SynchronizeWithRemoteUseCase = Mockito.mock()
    private lateinit var viewModel: HabitsListViewModel

    private lateinit var habit1: Habit

    private lateinit var habit2: Habit

    @After
    fun tearDown() {
        Mockito.reset(doneHabitUseCase)
        Mockito.reset(deleteHabitUseCase)
        Mockito.reset(sortHabitsUseCase)
        Mockito.reset(searchHabitsUseCase)
        Mockito.reset(getAllHabitsUseCase)
        Mockito.reset(synchronizeWithRemoteUseCase)
    }


    @Before
    fun setUp() {

        viewModel = HabitsListViewModel(
            doneHabitUseCase = doneHabitUseCase,
            deleteHabitUseCase = deleteHabitUseCase,
            sortHabitsUseCase = sortHabitsUseCase,
            searchHabitsUseCase = searchHabitsUseCase,
            getAllHabitsUseCase = getAllHabitsUseCase,
            synchronizeWithRemoteUseCase = synchronizeWithRemoteUseCase
        )

        habit1 = Habit(
            uid = "1",
            name = "name1",
            description = "description1",
            type = Type.GOOD,
            priority = Priority.IMPORTANT,
            frequency = Frequency.DAY,
            amount = 10,
            color = Constants.DEF_COLOR,
            date = 1,
            doneDates = mutableListOf(),
            id = 1
        )


        habit2 = Habit(
            uid = "2",
            name = "name2",
            description = "description1",
            type = Type.GOOD,
            priority = Priority.IMPORTANT,
            frequency = Frequency.WEEK,
            amount = 10,
            color = Constants.DEF_COLOR,
            date = 2,
            doneDates = mutableListOf(),
            id = 2
        )
    }

    @Test
    fun dismissState_isCorrect() = runBlocking {

        Mockito.`when`(doneHabitUseCase(habit1)).thenReturn(Unit)

        viewModel.doneHabit(habit1)

        val expected1 = HabitsListViewModel.HabitsListUiState(
            showToast = true,
            toastState = HabitsListViewModel.ToastState.GOOD_LESS,
            leftToDo = 9
        )

        Assert.assertEquals(expected1, viewModel.uiState.value)

        viewModel.dismissState()

        val expected2 = HabitsListViewModel.HabitsListUiState(
            showToast = false,
            toastState = viewModel.uiState.value.toastState,
            leftToDo = 0
        )

        Assert.assertEquals(expected2, viewModel.uiState.value)

    }



}