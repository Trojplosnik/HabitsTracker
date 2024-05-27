package com.example.habitstracker.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface IHabitDao {

    @Query("SELECT * FROM habits_table")
    fun getAll(): Flow<List<HabitEntity>>


    @Query("SELECT * FROM habits_table WHERE id = :id")
    fun getHabitById(id: Int): HabitEntity



    @Query("SELECT * FROM habits_table WHERE name LIKE '%' || :query || '%'")
    fun searchDatabase(query: String): List<HabitEntity>


    @Query("SELECT * FROM habits_table ORDER BY amount ASC")
    fun sortDatabaseASC(): List<HabitEntity>


    @Query("SELECT * FROM habits_table ORDER BY amount DESC")
    fun sortDatabaseDESC(): List<HabitEntity>


    @Insert
    suspend fun addHabit(habit: HabitEntity)


    @Delete
    suspend fun deleteHabit(habit: HabitEntity)


    @Query("DELETE FROM habits_table")
    suspend fun deleteAll()


    @Update
    suspend fun updateHabit(habit: HabitEntity)

}