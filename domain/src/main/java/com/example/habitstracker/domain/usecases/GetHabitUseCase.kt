package com.example.habitstracker.domain.usecases

import com.example.habitstracker.domain.repositories.IHabitsRepository
import javax.inject.Inject

class GetHabitUseCase @Inject constructor(private val repository: IHabitsRepository) {
     operator fun invoke(habitId: Int) = repository.getHabitById(habitId)
}