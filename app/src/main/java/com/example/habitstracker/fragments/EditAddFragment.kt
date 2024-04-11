package com.example.habitstracker.fragments


import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.children
import androidx.core.view.doOnLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habitstracker.Constants
import com.example.habitstracker.model.Habit
import com.example.habitstracker.R
import com.example.habitstracker.databinding.FragmentEditAddBinding
import com.example.habitstracker.viewModels.EditEddViewModel


class EditAddFragment : Fragment() {

    private var _viewModel: EditEddViewModel? = null
    private val viewModel
        get() = _viewModel ?: throw IllegalStateException("EditEddViewModel is null")


    private var _binding: FragmentEditAddBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentAddEditBinding is null")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = ViewModelProvider(this)[EditEddViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditAddBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }


    private fun setColorPickerConfig() {
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

    private fun setSaveButtonConfig() {
        with(binding) {
            btnSave.setOnClickListener {
                if (checkHabitIsEmpty())
                    Toast.makeText(
                        activity,
                        R.string.toast_fill_fields,
                        Toast.LENGTH_LONG
                    ).show()
                else {
                    viewModel.addEditHabit(
                        Habit(
                            name = etHabitName.text.toString(),
                            description = etHabitDesc.text.toString(),
                            amount = etAmount.text.toString(),
                            frequency = etFrequency.text.toString(),
                            priority = spnHabitPriority.selectedItem.toString(),
                            type = if (rBtnBad.isChecked) rBtnBad.text.toString()
                            else if (rBtnGood.isChecked) rBtnGood.text.toString()
                            else "Not selected",
                            color = cvCurrentColor.cardBackgroundColor.defaultColor
                        )
                    )
                    viewModel.mutableEditHabit.value = false
                    findNavController().navigate(R.id.mainFragment)
                }
            }
        }
    }

    private fun init() {

        setColorPickerConfig()
        setSaveButtonConfig()
        binding.btnClose.setOnClickListener {
            viewModel.mutableEditHabit.value = false
            findNavController().navigate(R.id.mainFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            if (it.containsKey(Constants.KEY_EXTRA_EDIT_HABIT)) {
                viewModel.setHabit(it.getInt(Constants.KEY_EXTRA_EDIT_HABIT))
                setEditHabitData(viewModel.getEditHabit())
            }
        }
    }

    private fun setEditHabitData(habit: Habit) {
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
}