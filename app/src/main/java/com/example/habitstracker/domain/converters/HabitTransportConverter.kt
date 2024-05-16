package com.example.habitstracker.domain.converters

import androidx.room.TypeConverter
import com.example.habitstracker.data.remote.model.HabitDto
import com.example.habitstracker.domain.entities.Habit
import com.example.habitstracker.domain.entities.Priority
import com.example.habitstracker.domain.entities.Type
import javax.inject.Singleton

@Singleton
class HabitTransportConverter {
    @TypeConverter
    fun fromTransport(habitDto: HabitDto): Habit {
        return Habit(
            priority = Priority.entries[habitDto.priority],
            type = Type.entries[habitDto.type],
            name = habitDto.title,
            description = habitDto.description,
            amount = habitDto.count,
            frequency = habitDto.frequency,
            date = habitDto.date,
            doneDates = habitDto.doneDates,
            uid = habitDto.uid
        )
    }
    @TypeConverter
    fun toTransport(habit: Habit): HabitDto {
        return HabitDto(
            count = habit.amount,
            date = habit.date,
            description = habit.description.ifEmpty { "no description" },
            frequency = habit.frequency,
            priority = habit.priority.ordinal,
            title = habit.name,
            type = habit.type.ordinal,
            doneDates = habit.doneDates,
            uid = habit.uid
        )
    }
}