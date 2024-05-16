package com.example.habitstracker.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.habitstracker.domain.entities.Habit
import kotlinx.coroutines.flow.Flow


@Dao
interface IHabitDao {

    @Insert
    suspend fun addHabit(habit: Habit)

    @Query("SELECT * FROM habits_table")
    fun getAll(): Flow<List<Habit>>

    @Query("SELECT * FROM habits_table WHERE id = :id")
    fun getHabitById(id: Int): Habit


    @Query("DELETE FROM habits_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM habits_table WHERE name LIKE '%' || :query || '%'")
    fun searchDatabase(query: String): List<Habit>



    @Query("SELECT * FROM habits_table ORDER BY amount ASC")
    fun sortDatabaseASC(): List<Habit>

    @Query("SELECT * FROM habits_table ORDER BY amount DESC")
    fun sortDatabaseDESC(): List<Habit>

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Update
    suspend fun updateHabit(habit: Habit)

}