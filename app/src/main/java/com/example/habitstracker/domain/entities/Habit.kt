package com.example.habitstracker.domain.entities
import android.graphics.Color
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.habitstracker.data.remote.model.HabitDto
import com.example.habitstracker.domain.converters.ListConverter
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "habits_table")
@TypeConverters(ListConverter::class)
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val amount: Int = 0,
    val frequency: Int = 0,
    val type: Type = Type.GOOD,
    val priority: Priority = Priority.NOT_SELECTED,
    val color: Int = Color.GRAY,
    val date: Int = 0,
    val uid: String = "",
    @TypeConverters(ListConverter::class) val doneDates: List<Int> = emptyList()
): Parcelable
