package com.example.habitstracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.habitstracker.domain.entities.Habit


@Database(entities = [Habit::class], version = 1, exportSchema = false)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): IHabitDao
}