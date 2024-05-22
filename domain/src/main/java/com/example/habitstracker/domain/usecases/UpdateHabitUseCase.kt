package com.example.habitstracker.domain.usecases

import com.example.habitstracker.domain.converters.DateTimeConverter
import com.example.habitstracker.domain.entities.Habit
import com.example.habitstracker.domain.repositories.IHabitsRepository
import javax.inject.Inject

class UpdateHabitUseCase @Inject constructor(
    private val repository: IHabitsRepository,
    private val dateTimeConverter: DateTimeConverter
) {
    suspend fun execute(habit: Habit) =
        repository.updateHabit(habit.copy(date = dateTimeConverter.currentDateTimeToInt()))
}