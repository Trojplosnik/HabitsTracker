package com.example.habitstracker.di

import android.content.Context
import androidx.room.Room
import com.example.habitstracker.data.local.HabitDatabase
import com.example.habitstracker.data.local.IHabitDao
import com.example.habitstracker.data.remote.IHabitService
import com.example.habitstracker.data.remote.RemoteDataSource
import com.example.habitstracker.data.repositories.HabitsRepositoryImpl
import com.example.habitstracker.domain.Constants.BASE_URL
import com.example.habitstracker.domain.converters.HabitTransportConverter
import com.example.habitstracker.domain.repositories.IHabitsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun providesHabitDao(database: HabitDatabase): IHabitDao = database.habitDao()


    @Singleton
    @Provides
    fun providesHabitTransportConverter(): HabitTransportConverter = HabitTransportConverter()


    @Singleton
    @Provides
    fun providesRemoteDataSource(habitService: IHabitService): RemoteDataSource =
        RemoteDataSource(habitService)

    @Singleton
    @Provides
    fun providesHabitsRepository(
        remoteDataSource: RemoteDataSource,
        habitsDao: IHabitDao,
        habitTransportConverter: HabitTransportConverter
    ): IHabitsRepository = HabitsRepositoryImpl(
        remoteDataSource = remoteDataSource,
        habitsDao = habitsDao,
        habitTransportConverter = habitTransportConverter
    )

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()


    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()


    @Singleton
    @Provides
    fun providesHabitService(retrofit: Retrofit): IHabitService =
        retrofit.create(IHabitService::class.java)

    @Singleton
    @Provides
    fun providesHabitDatabase(@ApplicationContext context: Context): HabitDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            HabitDatabase::class.java,
            "habit_database"
        ).build()
    }
}