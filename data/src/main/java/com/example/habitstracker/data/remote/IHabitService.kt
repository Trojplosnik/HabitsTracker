package com.example.habitstracker.data.remote


import com.example.habitstracker.data.Constants
import com.example.habitstracker.data.remote.entities.DoneHabit
import com.example.habitstracker.data.remote.entities.HabitDto
import com.example.habitstracker.data.remote.entities.UidHabit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface IHabitService {
    @GET("habit")
    suspend fun getHabits(@Header(Constants.AUTH_HEADER) authorization: String): List<HabitDto>

    @PUT("habit")
    suspend fun putHabit(
        @Header(Constants.AUTH_HEADER) authorization: String,
        @Body body: HabitDto
    ): UidHabit

    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    suspend fun deleteHabit(
        @Header(Constants.AUTH_HEADER) authorization: String,
        @Body body: UidHabit
    ): String

    @POST("habit")
    suspend fun habitDone(
        @Header(Constants.AUTH_HEADER) authorization: String,
        @Body body: DoneHabit
    ): String
}