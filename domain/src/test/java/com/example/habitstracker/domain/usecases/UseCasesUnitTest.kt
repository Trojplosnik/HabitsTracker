package com.example.habitstracker.domain.usecases

import com.example.habitstracker.domain.Constants
import com.example.habitstracker.domain.entities.Frequency
import com.example.habitstracker.domain.entities.Habit
import com.example.habitstracker.domain.entities.Priority
import com.example.habitstracker.domain.entities.Type
import com.example.habitstracker.domain.repositories.IHabitsRepository
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class UseCasesUnitTest {

    private val habitsRepository: IHabitsRepository = mock()

    private val habit1 = Habit(
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

    private val habit2 = Habit(
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

    @After
    fun tearDown() {
        Mockito.reset(habitsRepository)
    }



    @Test
    fun getAllHabitsUseCase_isCorrect() {
        Mockito.`when`(habitsRepository.getAllHabits())
            .thenReturn(flowOf(listOf(habit1, habit2)))

        val getAllHabitsUseCase = GetAllHabitsUseCase(habitsRepository)

        val expected = habitsRepository.getAllHabits()

        Assert.assertEquals(expected, getAllHabitsUseCase())
    }

    @Test
    fun getHabitUseCase_isCorrect() {
        Mockito.`when`(habitsRepository.getHabitById(2))
            .thenReturn(habit2)

        val getHabitUseCase = GetHabitUseCase(habitsRepository)

        val expected = habitsRepository.getHabitById(2)

        Assert.assertEquals(expected, getHabitUseCase(2))
    }

    @Test
    fun searchHabitsUseCaseWithEmptyQuery_isCorrect() {

        Mockito.`when`(habitsRepository.searchDatabase(""))
            .thenReturn(listOf(habit1, habit2))

        val searchHabitsUseCase = SearchHabitsUseCase(habitsRepository)

        val expected = habitsRepository.searchDatabase("")

        Assert.assertEquals(expected, searchHabitsUseCase(""))
    }

    @Test
    fun searchHabitsUseCaseWithNonEmptyQuery_isCorrect() {

        Mockito.`when`(habitsRepository.searchDatabase("1"))
            .thenReturn(listOf(habit1, habit2))

        val searchHabitsUseCase = SearchHabitsUseCase(habitsRepository)

        val expected = habitsRepository.searchDatabase("1")

        Assert.assertEquals(expected, searchHabitsUseCase("1"))
    }

    @Test
    fun searchHabitsUseCaseWithNonExistingHabitQuery_isCorrect() {

        Mockito.`when`(habitsRepository.searchDatabase("3"))
            .thenReturn(listOf(habit1, habit2))

        val searchHabitsUseCase = SearchHabitsUseCase(habitsRepository)

        val expected = habitsRepository.searchDatabase("3")

        Assert.assertEquals(expected, searchHabitsUseCase("3"))
    }

    @Test
    fun sortHabitsUseCaseDesc_isCorrect() {
        Mockito.`when`(habitsRepository.sortDatabaseDESC())
            .thenReturn(listOf(habit2, habit1))

        val sortHabitsUseCase = SortHabitsUseCase(habitsRepository)


        val expected = habitsRepository.sortDatabaseDESC()

        Assert.assertEquals(expected, sortHabitsUseCase(true))
    }

    @Test
    fun sortHabitsUseCaseAsc_isCorrect() {
        Mockito.`when`(habitsRepository.sortDatabaseASC())
            .thenReturn(listOf(habit1, habit2))

        val sortHabitsUseCase = SortHabitsUseCase(habitsRepository)

        val expected = habitsRepository.sortDatabaseASC()

        Assert.assertEquals(expected, sortHabitsUseCase(false))
    }



}