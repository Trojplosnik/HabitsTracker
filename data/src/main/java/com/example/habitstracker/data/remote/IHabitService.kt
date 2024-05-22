package com.example.habitstracker.data.remote


import com.example.habitstracker.domain.entities.HabitDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

interface IHabitService {
    @Headers("accept: application/json")
    @GET("habit")
    suspend fun getHabits(@Header("Authorization") authorization: String): Response<List<HabitDto>>
    @Headers("accept: application/json")
    @PUT("habit")
    suspend fun putHabit(@Header("Authorization") authorization: String, @Body body: String): Response<String>
    @Headers("accept: application/json")
    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    suspend fun deleteHabit(@Header("Authorization") authorization: String, @Body body: String): Response<String>
    @Headers("accept: application/json")
    @POST("habit")
    suspend fun habitDone(@Header("Authorization") authorization: String, @Body body: String): Response<String>
}