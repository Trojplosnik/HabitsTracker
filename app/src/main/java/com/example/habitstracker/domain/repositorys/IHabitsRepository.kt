package com.example.habitstracker.domain.repositorys

import com.example.habitstracker.domain.entities.Habit
import kotlinx.coroutines.flow.Flow

interface IHabitsRepository {

    suspend fun updateHabit(habit: Habit)

    suspend fun addHabit(habit: Habit)

    suspend fun deleteHabit(habit: Habit)

    suspend fun doneHabit(habit: Habit)

    fun getHabitById(id: Int): Habit

    fun searchDatabase(query: String): List<Habit>

    fun sortDatabaseASC(): List<Habit>

    fun sortDatabaseDESC(): List<Habit>

    fun getAllHabits(): Flow<List<Habit>>

}