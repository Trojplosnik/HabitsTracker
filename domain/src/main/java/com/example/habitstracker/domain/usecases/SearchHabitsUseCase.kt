package com.example.habitstracker.domain.usecases

import com.example.habitstracker.domain.repositories.IHabitsRepository
import javax.inject.Inject

class SearchHabitsUseCase @Inject constructor(private val repository: IHabitsRepository) {
    operator fun invoke(query: String) = repository.searchDatabase(query)
}