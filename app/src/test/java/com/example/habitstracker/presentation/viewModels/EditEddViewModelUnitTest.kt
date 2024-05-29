package com.example.habitstracker.presentation.viewModels


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.habitstracker.domain.Constants
import com.example.habitstracker.domain.entities.Frequency
import com.example.habitstracker.domain.entities.Habit
import com.example.habitstracker.domain.entities.Priority
import com.example.habitstracker.domain.entities.Type
import com.example.habitstracker.domain.usecases.AddHabitUseCase
import com.example.habitstracker.domain.usecases.GetHabitUseCase
import com.example.habitstracker.domain.usecases.UpdateHabitUseCase
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class EditEddViewModelUnitTest {

    private val getHabitUseCase: GetHabitUseCase = Mockito.mock()
    private val updateHabitUseCase: UpdateHabitUseCase = Mockito.mock()
    private val addHabitUseCase: AddHabitUseCase = Mockito.mock()
    private lateinit var viewModel: EditEddViewModel

    private lateinit var habit1: Habit

    private lateinit var habit2: Habit

    @get:Rule
    val rule = InstantTaskExecutorRule()


    @Before
    fun setUp() {

        viewModel = EditEddViewModel(
            addHabitUseCase = addHabitUseCase,
            getHabitUseCase = getHabitUseCase,
            updateHabitUseCase = updateHabitUseCase
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

    @After
    fun tearDown() {
        Mockito.reset(getHabitUseCase)
        Mockito.reset(updateHabitUseCase)
        Mockito.reset(addHabitUseCase)
    }


    @Test
    fun setNotEmptyName_isCorrect() {
        viewModel.setName("123")

        val expected = Habit(name = "123")

        Assert.assertEquals(expected, viewModel.getCurrentHabit())
    }


    @Test
    fun setEmptyName_isCorrect() {
        viewModel.setName("")

        val expected = Habit()

        Assert.assertEquals(expected, viewModel.getCurrentHabit())
    }

    @Test
    fun setDescription_isCorrect() {
        viewModel.setDescription("123")

        val expected = Habit(description = "123")

        Assert.assertEquals(expected, viewModel.getCurrentHabit())
    }

    @Test
    fun setAmount_isCorrect() {
        viewModel.setAmount("123")

        val expected = Habit(amount = 123)

        Assert.assertEquals(expected, viewModel.getCurrentHabit())
    }


    @Test
    fun setEmptyAmount_isCorrect() {
        viewModel.setAmount("")

        val expected = Habit(amount = -1)

        Assert.assertEquals(expected, viewModel.getCurrentHabit())
    }


    @Test
    fun setFrequency_isCorrect() {
        viewModel.setFrequency(Frequency.WEEK)

        val expected = Habit(frequency = Frequency.WEEK)

        Assert.assertEquals(expected, viewModel.getCurrentHabit())
    }

    @Test
    fun setPriority_isCorrect() {
        viewModel.setPriority(Priority.NOT_SELECTED)

        val expected = Habit(priority = Priority.NOT_SELECTED)

        Assert.assertEquals(expected, viewModel.getCurrentHabit())
    }

    @Test
    fun setType_isCorrect() {
        viewModel.setType(Type.BAD)

        val expected = Habit(type = Type.BAD)

        Assert.assertEquals(expected, viewModel.getCurrentHabit())
    }

    @Test
    fun setNotSelectedType_isCorrect() {
        viewModel.setType(Type.NOT_SELECTED)

        val expected = Habit()

        Assert.assertEquals(expected, viewModel.getCurrentHabit())
    }

    @Test
    fun setColor_isCorrect() {
        viewModel.setColor(0)

        val expected = Habit(color = 0)

        Assert.assertEquals(expected, viewModel.getCurrentHabit())
    }


    @Test
    fun getHabitById_isCorrect() = runTest {
        Mockito.`when`(getHabitUseCase(1)).thenReturn(habit1)
        val viewModel2 = EditEddViewModel(getHabitUseCase, updateHabitUseCase, addHabitUseCase)
        viewModel2.getHabitById(1)

        val expected = habit1

        Assert.assertEquals(expected, viewModel2.getCurrentHabit())
    }


    @Test
    fun sendToDataBaseEmptyInput_isCorrect() {

        viewModel.sendToDataBase()

        val actual = EditEddViewModel.EditEddUiState(
            isSaving = true,
            showToast = true,
            habitLoaded = false,
            changeColor = false
        )

        Assert.assertEquals(viewModel.uiState.value, actual)

    }

    @Test
    fun sendToDataBaseNotEmptyInput_isCorrect() {

        viewModel.setName("asda")
        viewModel.setType(Type.GOOD)

        viewModel.sendToDataBase()

        val actual = EditEddViewModel.EditEddUiState(
            isSaving = true,
            showToast = false,
            habitLoaded = false,
            changeColor = false
        )

        Assert.assertEquals(viewModel.uiState.value, actual)

    }


    @Test
    fun dismissState_isCorrect() {
        viewModel.sendToDataBase()

        viewModel.dismissState()

        Assert.assertEquals(viewModel.uiState.value, EditEddViewModel.EditEddUiState())

    }
}