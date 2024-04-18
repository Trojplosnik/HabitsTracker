package com.example.habitstracker.model
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "habits_table")
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val amount: String,
    val frequency: String,
    val type: String,
    val priority: String,
    val color: Int
): Parcelable
