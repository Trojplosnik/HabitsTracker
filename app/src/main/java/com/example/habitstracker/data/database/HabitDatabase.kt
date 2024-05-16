package com.example.habitstracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.habitstracker.domain.entities.Habit


@Database(entities = [Habit::class], version = 1, exportSchema = false)
abstract class HabitDatabase: RoomDatabase() {

    abstract fun habitDao(): IHabitDao

    companion object {
        @Volatile
        private var INSTANCE: HabitDatabase? = null

        fun getDatabase(context: Context): HabitDatabase
        {
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    HabitDatabase::class.java,
                    "habit_database"
                ).build().also { INSTANCE = it }
            }
        }


    }
}