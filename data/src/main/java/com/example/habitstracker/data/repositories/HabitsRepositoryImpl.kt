package com.example.habitstracker.data.repositories


import com.example.habitstracker.data.local.IHabitDao
import com.example.habitstracker.data.remote.RemoteDataSource
import com.example.habitstracker.domain.converters.HabitTransportConverter
import com.example.habitstracker.domain.entities.Habit
import com.google.gson.Gson
import javax.inject.Inject


class HabitsRepositoryImpl @Inject constructor(
    private val habitTransportConverter: HabitTransportConverter,
    private val remoteDataSource: RemoteDataSource,
    private val habitsDao: IHabitDao
) : com.example.habitstracker.domain.repositories.IHabitsRepository {

    private val gson = Gson()



    override suspend fun synchronizeWithRemote() {
        habitsDao.getAll().collect { localHabits ->
            localHabits.forEach {
                habit: Habit -> if (!habit.onRemoteDatabase){
                val habitDto = habitTransportConverter.toTransport(habit)
                val response = remoteDataSource.putHabit(gson.toJson(habitDto))
                if (response.isSuccessful)
                {
                    habitsDao.updateHabit(habit.copy(onRemoteDatabase = true))
                }
                }
            }
        }
    }


    override suspend fun updateHabit(habit: Habit) {
        habitsDao.updateHabit(habit)
        val habitDto = habitTransportConverter.toTransport(habit)
        remoteDataSource.putHabit(gson.toJson(habitDto))
    }

    override suspend fun addHabit(habit: Habit) {
        habitsDao.addHabit(habit)
        val habitDto = habitTransportConverter.toTransport(habit)
        val response = remoteDataSource.putHabit(gson.toJson(habitDto))
        if(response.isSuccessful)
        {
            updateHabit(habit.copy(onRemoteDatabase = true))
        }
    }

    override suspend fun deleteHabit(habit: Habit) {
        habitsDao.deleteHabit(habit)
        remoteDataSource.deleteHabit(gson.toJson(mapOf("uid" to habit.uid)))
    }

    override suspend fun doneHabit(habit: Habit) {
        habitsDao.updateHabit(habit)
        val response = remoteDataSource.habitDone(
            gson.toJson(
                mapOf(
                    "date" to habit.date,
                    "habit_uid" to habit.uid
                )
            )
        )
        if(response.isSuccessful)
        {
            updateHabit(habit.copy(onRemoteDatabase = true))
        }
    }

    override fun getHabitById(id: Int) = habitsDao.getHabitById(id)


    override fun searchDatabase(query: String) = habitsDao.searchDatabase(query)

    override fun sortDatabaseASC() = habitsDao.sortDatabaseASC()

    override fun sortDatabaseDESC() = habitsDao.sortDatabaseDESC()


    override fun getAllHabits() = habitsDao.getAll()


}