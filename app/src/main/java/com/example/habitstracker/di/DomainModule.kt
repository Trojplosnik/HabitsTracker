package com.example.habitstracker.di

import com.example.habitstracker.domain.converters.DateTimeConverter
import com.example.habitstracker.domain.repositories.IHabitsRepository
import com.example.habitstracker.domain.usecases.AddHabitUseCase
import com.example.habitstracker.domain.usecases.DeleteHabitUseCase
import com.example.habitstracker.domain.usecases.DoneHabitUseCase
import com.example.habitstracker.domain.usecases.GetAllHabitsUseCase
import com.example.habitstracker.domain.usecases.GetHabitUseCase
import com.example.habitstracker.domain.usecases.SearchHabitsUseCase
import com.example.habitstracker.domain.usecases.SortHabitsUseCase
import com.example.habitstracker.domain.usecases.SynchronizeWithRemoteUseCase
import com.example.habitstracker.domain.usecases.UpdateHabitUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Singleton
    @Provides
    fun providesAddHabitUseCase(
        repository: IHabitsRepository,
        dateTimeConverter: DateTimeConverter
    ) = AddHabitUseCase(repository = repository, dateTimeConverter = dateTimeConverter)


    @Singleton
    @Provides
    fun providesDeleteHabitUseCase(repository: IHabitsRepository) = DeleteHabitUseCase(repository)


    @Singleton
    @Provides
    fun providesDateTimeConverter(): DateTimeConverter = DateTimeConverter()


    @Singleton
    @Provides
    fun providesUpdateHabitUseCase(
        repository: IHabitsRepository,
        dateTimeConverter: DateTimeConverter
    ) = UpdateHabitUseCase(repository = repository, dateTimeConverter = dateTimeConverter)


    @Singleton
    @Provides
    fun providesSynchronizeWithRemoteUseCase(repository: IHabitsRepository) =
        SynchronizeWithRemoteUseCase(repository)


    @Singleton
    @Provides
    fun providesDoneHabitUseCase(
        repository: IHabitsRepository,
        dateTimeConverter: DateTimeConverter
    ) = DoneHabitUseCase(repository = repository, dateTimeConverter = dateTimeConverter)


    @Singleton
    @Provides
    fun providesGetHabitUseCase(repository: IHabitsRepository) = GetHabitUseCase(repository)


    @Singleton
    @Provides
    fun providesSearchHabitsUseCase(repository: IHabitsRepository) = SearchHabitsUseCase(repository)


    @Singleton
    @Provides
    fun providesSortHabitsUseCase(repository: IHabitsRepository) = SortHabitsUseCase(repository)

    @Singleton
    @Provides
    fun providesGetAllHabitsUseCase(repository: IHabitsRepository) = GetAllHabitsUseCase(repository)

}