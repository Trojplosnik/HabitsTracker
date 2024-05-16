package com.example.habitstracker.data.remote

import com.example.habitstracker.data.remote.model.HabitDto
import com.example.habitstracker.data.remote.model.InfoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface IHabitService {

    @GET("habit")
    suspend fun getHabits(@Header("Authorization") authorization: String): Response<List<HabitDto>>

    @PUT("habit")
    suspend fun putHabit(@Header("Authorization") authorization: String, @Body body: String): Response<InfoDto>

    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    suspend fun deleteHabit(@Header("Authorization") authorization: String, @Body body: String):  Response<InfoDto>

    @POST("habit")
    suspend fun habitDone(@Header("Authorization") authorization: String, @Body body: String) :  Response<InfoDto>
}