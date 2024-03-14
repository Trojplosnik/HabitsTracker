package com.example.habitstracker

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red

import com.example.habitstracker.databinding.ActivityAddEditBinding
import kotlin.IllegalStateException


class EditAddActivity : AppCompatActivity() {

    private var _binding: ActivityAddEditBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("ActivityAddEditBinding is null")

    private var habit: Habit = Habit()

    private var resultCode: Int = Constants.ADD_HABIT



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.hasExtra("edit_habit")) {
            resultCode = Constants.EDIT_HABIT
            habit = intent?.getSerializableExtra("edit_habit") as? Habit ?: Habit()
            with(binding) {
                with(habit) {
                    etHabitName.setText(name)
                    etHabitDesc.setText(description)
                    etAmount.setText(amount)
                    etFrequency.setText(frequency)
                    spnHabitPriority.setSelection(
                        resources.getStringArray(R.array.habit_priority).indexOf(priority)
                    )
                    when (type) {
                        rBtnBad.text.toString() -> rGrpType.check(rBtnBad.id)
                        rBtnGood.text.toString() -> rGrpType.check(rBtnGood.id)
                        else -> rGrpType.clearCheck()
                    }
                }
            }
        }

        init()
    }


    private fun init() {
        with(binding)
        {

//            for (i in 0 until (linL?.childCount ?: 0)) {
//                val child = linL?.getChildAt(i) ?: throw IllegalStateException("linL in sv")
//                child.setOnClickListener {
//                    val bitmap = Bitmap.createBitmap(it.width, it.height, Bitmap.Config.ARGB_8888)
//                    val pixel = bitmap.getPixel(it.width / 2, it.height / 2)
//                    val color = Color.argb(Color.alpha(pixel),Color.red(pixel), Color.green(pixel), Color.blue(pixel))
//                    Log.d("MyLog", "$color")
//                    cvCurrentColor.setCardBackgroundColor(color)
//                }
//            }




            btnClose.setOnClickListener {
                setResult(RESULT_CANCELED)
                finish()
            }


            btnSave.setOnClickListener {

                with(habit) {
                    etHabitName.text.toString().takeIf { it.isNotEmpty() }?.let { name = it }
                    etHabitDesc.text.toString().takeIf { it.isNotEmpty() }?.let { description = it }
                    etAmount.text.toString().takeIf { it.isNotEmpty() }?.let { amount = it }
                    etFrequency.text.toString().takeIf { it.isNotEmpty() }?.let { frequency = it }
                    priority = spnHabitPriority.selectedItem.toString()
                    type =
                        if (rBtnBad.isChecked) rBtnBad.text.toString()
                        else if (rBtnGood.isChecked) rBtnGood.text.toString()
                        else "Not selected"
                    color = Color.LTGRAY
                }

                setResult(
                    resultCode, Intent(
                        this@EditAddActivity,
                        MainActivity::class.java
                    )
                        .putExtra("habit", habit)
                )
                finish()
            }
        }
    }


}