package com.example.habitstracker.presentation.ui


import com.example.habitstracker.R
import com.example.habitstracker.presentation.fragments.EditAddFragment
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KButton

class EditAddScreen: KScreen<EditAddScreen>() {

    override val layoutId: Int = R.layout.fragment_edit_add
    override val viewClass: Class<*> = EditAddFragment::class.java


    val name = KEditText {
        withId(R.id.etHabitName)
    }

    val description = KEditText {
        withId(R.id.etHabitDesc)
    }


    val amount = KEditText {
        withId(R.id.etAmount)
    }

    val frequency = KView {
        withId(R.id.spnFrequency)
    }

    val priority = KView {
        withId(R.id.spnPriority)
    }

    val save = KButton {
        withId(R.id.btnSave)
    }

    val close = KButton {
        withId(R.id.btnClose)
    }

    val goodRadio = KView {
        withId(R.id.rBtnGood)
    }

    val badRadio = KView {
        withId(R.id.rBtnBad)
    }
}