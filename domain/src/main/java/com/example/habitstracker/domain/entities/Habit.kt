package com.example.habitstracker.domain.entities
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
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
    val frequency: Frequency = Frequency.DAY,
    val type: Type = Type.NOT_SELECTED,
    val priority: Priority = Priority.NOT_SELECTED,
    val color: Int = 0,
    val date: Int = 0,
    val uid: String = "",
    val doneDates: MutableList<Int> = emptyList<Int>().toMutableList(),
    val onRemoteDatabase: Boolean = false
): Parcelable
