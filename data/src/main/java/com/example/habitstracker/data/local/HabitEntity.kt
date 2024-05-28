package com.example.habitstracker.data.local

import com.example.habitstracker.domain.entities.Frequency
import com.example.habitstracker.domain.entities.Priority
import com.example.habitstracker.domain.entities.Type


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.habitstracker.data.converters.ListConverter
import com.example.habitstracker.domain.Constants


@Entity(tableName = "habits_table")
@TypeConverters(ListConverter::class)
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val amount: Int = 0,
    val frequency: Frequency = Frequency.DAY,
    val type: Type = Type.NOT_SELECTED,
    val priority: Priority = Priority.NOT_SELECTED,
    val color: Int = Constants.DEF_COLOR,
    val date: Int = 0,
    val uid: String = "",
    val doneDates: MutableList<Int> = emptyList<Int>().toMutableList()
)

