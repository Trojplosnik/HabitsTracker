package com.example.habitstracker.data.converters

import com.example.habitstracker.data.local.HabitEntity
import com.example.habitstracker.data.remote.entities.HabitDto
import com.example.habitstracker.domain.entities.Frequency
import com.example.habitstracker.domain.entities.Habit
import com.example.habitstracker.domain.entities.Priority
import com.example.habitstracker.domain.entities.Type

fun Habit.toTransport(): HabitDto = HabitDto(
        count = this.amount,
        date = this.date,
        description = this.description.ifEmpty { "no description" },
        frequency = this.frequency.ordinal,
        priority = this.priority.ordinal,
        title = this.name,
        type = this.type.ordinal,
        doneDates = this.doneDates,
        uid = this.uid,
        color = this.color
    )

fun HabitDto.fromTransport(): Habit = Habit(
        priority = Priority.entries[this.priority],
        type = Type.entries[this.type],
        name = this.title,
        description = this.description,
        amount = this.count,
        frequency = Frequency.entries[this.frequency],
        date = this.date,
        doneDates = this.doneDates.toMutableList(),
        uid = this.uid,
        color = this.color
    )


fun Habit.toEntity(): HabitEntity = HabitEntity(
        priority = this.priority,
        type = this.type,
        name = this.name,
        description = this.description,
        amount = this.amount,
        frequency = this.frequency,
        date = this.date,
        doneDates = this.doneDates.toMutableList(),
        uid = this.uid,
        color = this.color,
        id = this.id,
        onRemoteDatabase = onRemoteDatabase
)


fun HabitEntity.fromEntity(): Habit = Habit(
        priority = this.priority,
        type = this.type,
        name = this.name,
        description = this.description,
        amount = this.amount,
        frequency = this.frequency,
        date = this.date,
        doneDates = this.doneDates.toMutableList(),
        uid = this.uid,
        color = this.color,
        id = this.id,
        onRemoteDatabase = onRemoteDatabase
)

fun HabitEntity.toTransport(): HabitDto = HabitDto(
        count = this.amount,
        date = this.date,
        description = this.description.ifEmpty { "no description" },
        frequency = this.frequency.ordinal,
        priority = this.priority.ordinal,
        title = this.name,
        type = this.type.ordinal,
        doneDates = this.doneDates,
        uid = this.uid,
        color = this.color
)


fun List<HabitEntity>.fromEntityList(): List<Habit> = this.map { habitEntity -> habitEntity.fromEntity() }


