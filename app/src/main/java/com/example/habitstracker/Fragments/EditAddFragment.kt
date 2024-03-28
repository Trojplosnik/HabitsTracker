package com.example.habitstracker.Fragments


import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.children
import androidx.core.view.doOnLayout
import androidx.navigation.Navigation
import com.example.habitstracker.Constants
import com.example.habitstracker.Habit
import com.example.habitstracker.R
import com.example.habitstracker.databinding.FragmentEditAddBinding



class EditAddFragment : Fragment() {
    private var _binding: FragmentEditAddBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentAddEditBinding is null")


    private var habit: Habit = Habit()

    private var resultCode: String = Constants.KEY_EXTRA_ADD_HABIT


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        arguments?.let {

                if(it.containsKey(Constants.KEY_EXTRA_EDIT_HABIT)) {
                    resultCode = Constants.KEY_EXTRA_EDIT_HABIT
                    habit = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        it.getSerializable(Constants.KEY_EXTRA_EDIT_HABIT, Habit::class.java) ?: Habit()
                    }
                    else it.getSerializable(Constants.KEY_EXTRA_EDIT_HABIT) as? Habit ?: Habit()
                }
            else habit = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getSerializable(Constants.KEY_EXTRA_ADD_HABIT, Habit::class.java) ?: Habit()
                }
                else it.getSerializable(Constants.KEY_EXTRA_ADD_HABIT) as? Habit ?: Habit()

        }
    }

    private fun setEditHabitData() {
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
                cvCurrentColor.setCardBackgroundColor(color)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditAddBinding.inflate(inflater, container, false)

        init()

        if (habit.name != "")
            setEditHabitData()



        return binding.root
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

    private fun checkHabitIsEmpty() = with(binding) {
        etHabitName.text.isEmpty() or
                etAmount.text.isEmpty() or
                etFrequency.text.isEmpty() or
                (rGrpType.checkedRadioButtonId == -1)
    }

    private fun setSaveButtonConfig()
    {
        with(binding) {
            btnSave.setOnClickListener {
                if (checkHabitIsEmpty())
                    Toast.makeText(activity,
                        R.string.toast_fill_fields,
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
                    val bundle = Bundle()
                    bundle.putSerializable(resultCode, habit)
                    Navigation.findNavController(binding.root).navigate(R.id.mainFragment, bundle)
                }
            }
        }
    }

    private fun init() {

        setColorPickerConfig()
        setSaveButtonConfig()
        binding.btnClose.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.mainFragment)
        }
    }


}