package com.example.habitstracker.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.habitstracker.interfaces.IHabitDao


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
                ).allowMainThreadQueries().build().also { INSTANCE = it }
            }
        }


    }
}