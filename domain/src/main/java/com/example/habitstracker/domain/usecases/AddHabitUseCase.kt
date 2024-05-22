package com.example.habitstracker.domain.usecases

import com.example.habitstracker.domain.converters.DateTimeConverter
import com.example.habitstracker.domain.entities.Habit
import com.example.habitstracker.domain.repositories.IHabitsRepository
import javax.inject.Inject

class AddHabitUseCase @Inject constructor(
    private val repository: IHabitsRepository,
    private val dateTimeConverter: DateTimeConverter
) {
    suspend fun execute(habit: Habit) =
        repository.addHabit(habit.copy(date = dateTimeConverter.currentDateTimeToInt()))
}