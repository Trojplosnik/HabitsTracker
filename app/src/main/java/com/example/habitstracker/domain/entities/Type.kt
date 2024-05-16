package com.example.habitstracker.domain.entities


import java.util.Locale

enum class Type {
    GOOD,
    BAD,
    NOT_SELECTED;

    fun getType(): String = name.lowercase(Locale.ROOT)
}