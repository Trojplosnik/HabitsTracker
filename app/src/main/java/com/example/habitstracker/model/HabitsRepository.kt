package com.example.habitstracker.model


import com.example.habitstracker.interfaces.IHabitDao

class HabitsRepository(private val habitsDao: IHabitDao){


    suspend fun updateHabit(habit: Habit) {
        habitsDao.updateHabit(habit)
    }

    suspend fun addHabit(habit: Habit) {
        habitsDao.addHabit(habit)
    }

    suspend fun deleteHabit(habit: Habit) {
        habitsDao.deleteHabit(habit)
    }

    fun searchDatabase(query: String) = habitsDao.searchDatabase(query)

    fun sortDatabaseASC() = habitsDao.sortDatabaseASC()

    fun sortDatabaseDESC() = habitsDao.sortDatabaseDESC()


    fun getAllHabits() = habitsDao.getAll()

    suspend fun deleteAll() {
        habitsDao.deleteAll()
    }


}