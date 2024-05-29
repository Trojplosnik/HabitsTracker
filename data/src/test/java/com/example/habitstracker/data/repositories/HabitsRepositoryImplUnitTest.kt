package com.example.habitstracker.data.repositories

import com.example.habitstracker.data.converters.DateTimeConverter
import com.example.habitstracker.data.converters.fromEntity
import com.example.habitstracker.data.converters.fromEntityList
import com.example.habitstracker.data.local.HabitEntity
import com.example.habitstracker.data.local.IHabitDao
import com.example.habitstracker.data.remote.RemoteDataSource
import com.example.habitstracker.domain.Constants
import com.example.habitstracker.domain.entities.Frequency
import com.example.habitstracker.domain.entities.Habit
import com.example.habitstracker.domain.entities.Priority
import com.example.habitstracker.domain.entities.Type
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock


class HabitsRepositoryImplUnitTest {

    private val remoteDataSource: RemoteDataSource = mock()
    private val habitsDao: IHabitDao = mock()
    private val dateTimeConverter: DateTimeConverter = mock()
    private lateinit var repository: HabitsRepositoryImpl


    private val habitEntity1 = HabitEntity(
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

    private val habitEntity2 = HabitEntity(
        uid = "2",
        name = "name2",
        description = "description2",
        type = Type.BAD,
        priority = Priority.IMPORTANT,
        frequency = Frequency.WEEK,
        amount = 10,
        color = Constants.DEF_COLOR,
        date = 1,
        doneDates = mutableListOf(),
        id = 2
    )


    @After
    fun tearDown() {
        Mockito.reset(remoteDataSource)
        Mockito.reset(habitsDao)
        Mockito.reset(dateTimeConverter)
    }


    @Before
    fun setUp() {
        repository = HabitsRepositoryImpl(remoteDataSource, habitsDao, dateTimeConverter)
    }



    @Test
    fun getHabitById_isCorrect() {
        Mockito.`when`(habitsDao.getHabitById(1)).thenReturn(habitEntity1)


        val actual = repository.getHabitById(1)

        val expected = habitEntity1.fromEntity()

        Assert.assertEquals(expected, actual)
    }


    @Test
    fun searchDatabaseWithNotEmptyQuery_isCorrect() {
        Mockito.`when`(habitsDao.searchDatabase("1")).thenReturn(listOf(habitEntity1))

        val actual = repository.searchDatabase("1")

        val expected = listOf(habitEntity1).fromEntityList()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun searchDatabaseWithNonExistHabitNameQuery_isCorrect() {
        Mockito.`when`(habitsDao.searchDatabase("3")).thenReturn(listOf())

        val actual = repository.searchDatabase("3")

        val expected = listOf<Habit>()

        Assert.assertEquals(expected, actual)
    }


    @Test
    fun searchDatabaseWithEmptyQuery_isCorrect() {
        Mockito.`when`(habitsDao.searchDatabase(""))
            .thenReturn(listOf(habitEntity1, habitEntity2))

        val actual = repository.searchDatabase("")

        val expected = listOf(habitEntity1, habitEntity2).fromEntityList()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun sortDatabaseASC_isCorrect() {
        Mockito.`when`(habitsDao.sortDatabaseASC())
            .thenReturn(listOf(habitEntity1, habitEntity2))

        val actual = repository.sortDatabaseASC()

        val expected = listOf(habitEntity1, habitEntity2).fromEntityList()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun sortDatabaseDESC_isCorrect() {
        Mockito.`when`(habitsDao.sortDatabaseDESC())
            .thenReturn(listOf(habitEntity2, habitEntity1))

        val actual = repository.sortDatabaseDESC()

        val expected = listOf(habitEntity2, habitEntity1).fromEntityList()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getAllHabits_isCorrect()  = runBlocking {
        Mockito.`when`(habitsDao.getAll())
            .thenReturn(flowOf(listOf(habitEntity1, habitEntity2)))


        val actual = repository.getAllHabits()

        val expected = listOf(habitEntity1, habitEntity2).fromEntityList()

        Assert.assertEquals(expected, actual.last())
    }
}