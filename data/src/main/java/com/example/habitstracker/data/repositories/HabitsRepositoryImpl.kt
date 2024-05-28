package com.example.habitstracker.data.repositories


import android.util.Log
import com.example.habitstracker.data.converters.DateTimeConverter
import com.example.habitstracker.data.converters.fromEntity
import com.example.habitstracker.data.local.IHabitDao
import com.example.habitstracker.data.remote.RemoteDataSource
import com.example.habitstracker.data.converters.fromEntityList
import com.example.habitstracker.data.converters.fromTransport
import com.example.habitstracker.data.converters.toEntity
import com.example.habitstracker.data.converters.toTransport
import com.example.habitstracker.data.local.HabitEntity
import com.example.habitstracker.data.remote.entities.DoneHabit
import com.example.habitstracker.data.remote.entities.HabitDto
import com.example.habitstracker.data.remote.entities.UidHabit
import com.example.habitstracker.domain.entities.Habit
import com.example.habitstracker.domain.repositories.IHabitsRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject


class HabitsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val habitsDao: IHabitDao,
    private val dateTimeConverter: DateTimeConverter
) : IHabitsRepository {


    override suspend fun synchronizeWithRemote() {
        var localHabits = habitsDao.getAll().firstOrNull()
        if (localHabits == null)
            localHabits = listOf()
        try {
            val remoteHabits = remoteDataSource.getAllHabits()
            if (localHabits.size > remoteHabits.size) {
                localHabits.forEach { habitEntity: HabitEntity ->
                    if (habitEntity.uid.isEmpty()) {
                        val habitDto = habitEntity.toTransport()
                        try {
                            val response = remoteDataSource.putHabit(habitDto)
                            habitsDao.updateHabit(
                                habitEntity.copy(
                                    uid = response.uid
                                )
                            )
                        } catch (e: HttpException) {
                            Log.e("Net", e.message())
                        }
                    }
                }
            } else if (localHabits.size < remoteHabits.size) {
                remoteHabits.forEach { habitDto: HabitDto ->
                    if (!localHabits.any { it.uid == habitDto.uid })
                        habitsDao.addHabit(habitDto.fromTransport())
                }
            }
        } catch (e: HttpException) {
            Log.e("Net", e.message())
        }
    }


    override suspend fun updateHabit(habit: Habit) {
        val updatedHabit = habit.copy(date = dateTimeConverter.currentDateTimeToInt())
        habitsDao.updateHabit(updatedHabit.toEntity())
        remoteDataSource.putHabit(updatedHabit.toTransport())
    }

    override suspend fun addHabit(habit: Habit) {
        val habitEntity = habit.toEntity()
        try {
            val response = remoteDataSource.putHabit(habitEntity.toTransport())
            habitsDao.addHabit(
                habitEntity.copy(
                    uid = response.uid
                )
            )
        } catch (e: HttpException) {
            Log.e("Net", e.message())
            habitsDao.addHabit(
                habitEntity
            )
        }
    }

    override suspend fun deleteHabit(habit: Habit) {
        habitsDao.deleteHabit(habit.toEntity())
        remoteDataSource.deleteHabit(UidHabit(habit.uid))
    }

    override suspend fun doneHabit(habit: Habit) {
        val now = dateTimeConverter.currentDateTimeToInt()
        if (habit.frequency.timeInSeconds < now - habit.date) {
            habit.doneDates.clear()
            habit.doneDates.add(now)
        } else {
            habit.doneDates.add(now)
        }
        habitsDao.updateHabit(habit.toEntity())
        remoteDataSource.habitDone(
            DoneHabit(date = habit.date, uid = habit.uid)
        )
    }

    override fun getHabitById(id: Int) = habitsDao.getHabitById(id).fromEntity()


    override fun searchDatabase(query: String) = habitsDao.searchDatabase(query).fromEntityList()

    override fun sortDatabaseASC() = habitsDao.sortDatabaseASC().fromEntityList()

    override fun sortDatabaseDESC() = habitsDao.sortDatabaseDESC().fromEntityList()


    override fun getAllHabits() = habitsDao.getAll().map { list -> list.fromEntityList() }


}