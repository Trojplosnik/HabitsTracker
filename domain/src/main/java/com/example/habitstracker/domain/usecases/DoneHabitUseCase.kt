package com.example.habitstracker.domain.usecases


import com.example.habitstracker.domain.converters.DateTimeConverter
import com.example.habitstracker.domain.entities.Habit
import com.example.habitstracker.domain.repositories.IHabitsRepository
import javax.inject.Inject

class DoneHabitUseCase @Inject constructor(
    private val repository: IHabitsRepository,
    private val dateTimeConverter: DateTimeConverter
) {

    suspend fun execute(habit: Habit) {

        val now = dateTimeConverter.currentDateTimeToInt()
        if (habit.frequency.timeInSeconds < now - habit.date) {
            habit.doneDates.clear()
            habit.doneDates.add(now)
            repository.doneHabit(habit.copy(date = now))
        }
        else {
            habit.doneDates.add(now)
            repository.doneHabit(habit)
        }
    }
}