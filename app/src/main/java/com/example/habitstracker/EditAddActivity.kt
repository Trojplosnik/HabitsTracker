package com.example.habitstracker

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.children
import androidx.core.view.doOnLayout
import com.example.habitstracker.databinding.ActivityAddEditBinding


class EditAddActivity : AppCompatActivity() {

    private var _binding: ActivityAddEditBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("ActivityAddEditBinding is null")

    private var habit: Habit = Habit()

    private var resultCode: Int = Constants.RESULT_CODE_ADD_HABIT


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkIntentData()
        init()
    }

    private fun checkIntentData() {
        if (intent.hasExtra(Constants.KEY_EXTRA_EDIT_HABIT)) {
            resultCode = Constants.RESULT_CODE_EDIT_HABIT
            @Suppress("DEPRECATION")
            habit =
                intent?.getSerializableExtra(Constants.KEY_EXTRA_EDIT_HABIT) as? Habit ?: Habit()
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
    }


    private fun checkHabitIsEmpty() = with(binding) {
        etHabitName.text.isEmpty() or
                etAmount.text.isEmpty() or
                etFrequency.text.isEmpty() or
                (rGrpType.checkedRadioButtonId == -1)
    }

    private fun setColorPickerConfig()
    {
        with(binding)
        {
            linL?.doOnLayout { layout ->
                val bitmap = layout.background.toBitmap(
                    layout.measuredWidth,
                    layout.measuredHeight, Bitmap.Config.ARGB_8888
                )
                linL.children.forEach { child ->
                    child.setOnClickListener {
                        val color = bitmap.getPixel(
                            (it.x + it.width / 2).toInt(),
                            (it.y + it.height / 2).toInt()
                        )
                        cvCurrentColor.setCardBackgroundColor(color)
                    }
                }
            }
        }
    }


    private fun setSaveButtonConfig()
    {
        with(binding) {
            btnSave.setOnClickListener {
                if (checkHabitIsEmpty())
                    Toast.makeText(
                        this@EditAddActivity, R.string.toast_fill_fields,
                        Toast.LENGTH_LONG
                    ).show()
                else {
                    with(habit) {
                        name = etHabitName.text.toString()
                        description = etHabitDesc.text.toString()
                        amount = etAmount.text.toString()
                        frequency = etFrequency.text.toString()
                        priority = spnHabitPriority.selectedItem.toString()
                        type =
                            if (rBtnBad.isChecked) rBtnBad.text.toString()
                            else if (rBtnGood.isChecked) rBtnGood.text.toString()
                            else "Not selected"
                        color = cvCurrentColor.cardBackgroundColor.defaultColor
                    }

                    setResult(
                        resultCode, Intent(
                            this@EditAddActivity,
                            MainActivity::class.java
                        ).putExtra(Constants.KEY_EXTRA_ADD_HABIT, habit)
                    )
                    finish()
                }
            }
        }
    }


    private fun View.hideKeyboard() {
        val imm = ContextCompat.getSystemService(context, InputMethodManager::class.java) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun init() {

        setColorPickerConfig()
        setSaveButtonConfig()
        binding.btnClose.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
        binding.root.setOnClickListener{
            currentFocus?.clearFocus()
            it.hideKeyboard()
        }
    }


}