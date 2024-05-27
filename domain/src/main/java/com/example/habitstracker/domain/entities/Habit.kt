package com.example.habitstracker.domain.entities
import android.os.Parcelable
import com.example.habitstracker.domain.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
data class Habit(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val amount: Int = 0,
    val frequency: Frequency = Frequency.DAY,
    val type: Type = Type.NOT_SELECTED,
    val priority: Priority = Priority.NOT_SELECTED,
    val color: Int = Constants.DEF_COLOR,
    val date: Int = 0,
    val uid: String = "",
    val doneDates: MutableList<Int> = emptyList<Int>().toMutableList(),
    val onRemoteDatabase: Boolean = false
): Parcelable
