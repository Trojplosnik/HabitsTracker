package com.example.habitstracker.data.repositories


import android.util.Log
import com.example.habitstracker.data.database.IHabitDao
import com.example.habitstracker.data.remote.RemoteDataSource
import com.example.habitstracker.domain.Constants
import com.example.habitstracker.domain.entities.Habit
import com.example.habitstracker.domain.repositorys.IHabitsRepository
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class HabitsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val habitsDao: IHabitDao
) : IHabitsRepository {

    private val gson = Gson()


    init {

        CoroutineScope(Dispatchers.IO).launch {
            habitsDao.getAll().collect { localHabits ->
                val remoteHabitsResponse = remoteDataSource.getAllHabits()
                if (remoteHabitsResponse.isSuccessful) {
                    val remoteHabits = remoteHabitsResponse.body()
                    if (remoteHabits != null) {
                        remoteHabits.forEach { remoteHabit ->
                            if (localHabits.none { it.uid == remoteHabit.uid }) {
                                habitsDao.addHabit(
                                    Constants.habitTransportConverter.fromTransport(
                                        remoteHabit
                                    )
                                )
                            }
                        }
                        localHabits.forEach { localHabit ->
                            if (remoteHabits.none { it.uid == localHabit.uid }) {
                                val habitDto =
                                    Constants.habitTransportConverter.toTransport(localHabit)
                                val gson = Gson()
                                Log.d("gson", gson.toJson(habitDto))
                                remoteDataSource.putHabit(gson.toJson(habitDto))
                            }
                        }
                    }
                }
            }
        }

    }

    override suspend fun updateHabit(habit: Habit) {
        habitsDao.updateHabit(habit)
        val habitDto = Constants.habitTransportConverter.toTransport(habit)
        Log.d("gson", gson.toJson(habitDto))
        remoteDataSource.putHabit(gson.toJson(habitDto))
    }

    override suspend fun addHabit(habit: Habit) {
        habitsDao.addHabit(habit)
        val habitDto = Constants.habitTransportConverter.toTransport(habit)
        Log.d("gson", gson.toJson(habitDto))
        remoteDataSource.putHabit(gson.toJson(habitDto))
    }

    override suspend fun deleteHabit(habit: Habit) {
        habitsDao.deleteHabit(habit)
        Log.d("gson", gson.toJson(mapOf("uid" to habit.uid)))
        remoteDataSource.deleteHabit(gson.toJson(mapOf("uid" to habit.uid)))
    }

    override suspend fun doneHabit(habit: Habit) {
        Log.d("gson", gson.toJson(mapOf("uid" to habit.uid)))
        remoteDataSource.habitDone(gson.toJson(mapOf("uid" to habit.uid)))
    }

    override fun getHabitById(id: Int) = habitsDao.getHabitById(id)


    override fun searchDatabase(query: String) = habitsDao.searchDatabase(query)

    override fun sortDatabaseASC() = habitsDao.sortDatabaseASC()

    override fun sortDatabaseDESC() = habitsDao.sortDatabaseDESC()


    override fun getAllHabits() = habitsDao.getAll()


}