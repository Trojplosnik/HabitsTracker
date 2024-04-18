package com.example.habitstracker.interfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.habitstracker.model.Habit


@Dao
interface IHabitDao {

    @Insert
    suspend fun addHabit(habit: Habit)

    @Query("SELECT * FROM habits_table")
    fun getAll(): LiveData<List<Habit>>

    @Query("DELETE FROM habits_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM habits_table WHERE name LIKE '%' || :query || '%'")
    fun searchDatabase(query: String): LiveData<List<Habit>>


    @Query("SELECT * FROM habits_table ORDER BY amount ASC")
    fun sortDatabaseASC(): LiveData<List<Habit>>

    @Query("SELECT * FROM habits_table ORDER BY amount DESC")
    fun sortDatabaseDESC(): LiveData<List<Habit>>

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Update
    suspend fun updateHabit(habit: Habit)

}