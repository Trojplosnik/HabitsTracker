package com.example.habitstracker.domain.usecases

import com.example.habitstracker.domain.repositories.IHabitsRepository
import javax.inject.Inject

class SynchronizeWithRemoteUseCase @Inject constructor(private val repository: IHabitsRepository)
{

    suspend fun execute() = repository.synchronizeWithRemote()
}