package com.example.habitstracker.domain.usecases

import com.example.habitstracker.domain.repositories.IHabitsRepository
import javax.inject.Inject

class SortHabitsUseCase @Inject constructor(private val repository: IHabitsRepository) {
    operator fun invoke(isDesc: Boolean) = if (isDesc)
        repository.sortDatabaseDESC()
    else
        repository.sortDatabaseASC()
}