package com.example.habitstracker.domain.entities

import java.util.Locale

enum class Priority {
    NOT_SELECTED,
    IMPORTANT,
    NOT_IMPORTANT;

    fun getPriority(): String = name.lowercase(Locale.ROOT)
}