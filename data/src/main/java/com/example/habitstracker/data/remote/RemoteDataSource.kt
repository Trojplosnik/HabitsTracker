package com.example.habitstracker.data.remote


import android.util.Log
import com.example.data.BuildConfig
import com.example.habitstracker.data.remote.entities.DoneHabit
import com.example.habitstracker.data.remote.entities.HabitDto
import com.example.habitstracker.data.remote.entities.UidHabit
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val habitService: IHabitService) {


    suspend fun getAllHabits(): List<HabitDto> = habitService.getHabits(BuildConfig.API_TOKEN)

    suspend fun deleteHabit(body: UidHabit) {
        try {
            habitService.deleteHabit(authorization = BuildConfig.API_TOKEN, body = body)
        }catch (e: HttpException) {
            Log.e("Net", e.message())
        }
    }

    suspend fun habitDone(body: DoneHabit) {
        try {
            habitService.habitDone(authorization = BuildConfig.API_TOKEN, body = body)
        }catch (e: HttpException) {
            Log.e("Net", e.message())
        }
    }

    suspend fun putHabit(body: HabitDto): UidHabit {
        Log.d("add", body.toString())
        return habitService.putHabit(authorization = BuildConfig.API_TOKEN, body = body)
    }

}