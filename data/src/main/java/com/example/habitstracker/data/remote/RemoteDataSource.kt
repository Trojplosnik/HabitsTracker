package com.example.habitstracker.data.remote



import com.example.data.BuildConfig
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val habitService: IHabitService) {

    private val token = BuildConfig.API_TOKEN


    suspend fun getAllHabits() = habitService.getHabits(token)

    suspend fun deleteHabit(body: String) = habitService.deleteHabit(authorization = token, body = body)

    suspend fun habitDone(body: String) = habitService.habitDone(authorization = token, body = body)

    suspend fun putHabit(body: String) = habitService.putHabit(authorization = token, body = body)
}