package com.example.habitstracker.presentation.ui


import com.example.habitstracker.R
import com.example.habitstracker.presentation.fragments.MainFragment
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView


class HabitsScreen : KScreen<HabitsScreen>() {

    override val layoutId: Int = R.layout.fragment_main
    override val viewClass: Class<*> = MainFragment::class.java


    val fab = KView {
        withId(R.id.fabAddHabit)
    }

}