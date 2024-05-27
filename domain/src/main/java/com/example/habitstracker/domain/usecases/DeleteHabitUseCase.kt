package com.example.habitstracker.domain.usecases

import com.example.habitstracker.domain.entities.Habit
import com.example.habitstracker.domain.repositories.IHabitsRepository
import javax.inject.Inject

class DeleteHabitUseCase @Inject constructor(private val repository: IHabitsRepository) {
    suspend operator fun invoke(habit: Habit) = repository.deleteHabit(habit)
}