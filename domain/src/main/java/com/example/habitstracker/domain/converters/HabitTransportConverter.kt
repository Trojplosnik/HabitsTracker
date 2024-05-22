package com.example.habitstracker.domain.converters

import androidx.room.TypeConverter
import com.example.habitstracker.domain.entities.Frequency
import com.example.habitstracker.domain.entities.Habit
import com.example.habitstracker.domain.entities.HabitDto
import com.example.habitstracker.domain.entities.Priority
import com.example.habitstracker.domain.entities.Type


class HabitTransportConverter {
    @TypeConverter
    fun fromTransport(habitDto: HabitDto): Habit {
        return Habit(
            priority = Priority.entries[habitDto.priority],
            type = Type.entries[habitDto.type],
            name = habitDto.title,
            description = habitDto.description,
            amount = habitDto.count,
            frequency = Frequency.entries[habitDto.frequency],
            date = habitDto.date,
            doneDates = habitDto.doneDates.toMutableList(),
            uid = habitDto.uid,
            color = habitDto.color
        )
    }
    @TypeConverter
    fun toTransport(habit: Habit): HabitDto {
        return HabitDto(
            count = habit.amount,
            date = habit.date,
            description = habit.description.ifEmpty { "no description" },
            frequency = habit.frequency.ordinal,
            priority = habit.priority.ordinal,
            title = habit.name,
            type = habit.type.ordinal,
            doneDates = habit.doneDates,
            uid = habit.uid,
            color = habit.color
        )
    }
}