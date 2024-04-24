package com.example.habitstracker.fragments


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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.habitstracker.Constants
import com.example.habitstracker.model.Habit
import com.example.habitstracker.R
import com.example.habitstracker.databinding.FragmentEditAddBinding
import com.example.habitstracker.model.HabitDatabase
import com.example.habitstracker.model.HabitsRepository
import com.example.habitstracker.viewModels.EditEddViewModel
import com.example.habitstracker.viewModels.factories.EditEddViewModelFactory


class EditAddFragment : Fragment() {




    private var _binding: FragmentEditAddBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentAddEditBinding is null")

    private val viewModel: EditEddViewModel by viewModels<EditEddViewModel> {
        EditEddViewModelFactory(HabitsRepository(HabitDatabase.getDatabase(requireContext()).habitDao()))
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
                    val color = bitmap.getPixel(
                        (child.x + child.width / 2).toInt(),
                        (child.y + child.height / 2).toInt()
                    )
                    child.setOnClickListener {
                        cvCurrentColor.setCardBackgroundColor(color)
                    }
                }
            }
        }
    }

    private fun checkInputIsEmpty() = with(binding) {
        etHabitName.text.isEmpty() ||
                etAmount.text.isEmpty() ||
                etFrequency.text.isEmpty() ||
                (rGrpType.checkedRadioButtonId == -1)
    }

    private fun setSaveButtonConfig() {
        with(binding) {
            btnSave.setOnClickListener {
                if (checkInputIsEmpty())
                    Toast.makeText(
                        activity,
                        R.string.toast_fill_fields,
                        Toast.LENGTH_LONG
                    ).show()
                else {
                    val habit =  Habit(
                        id = viewModel.currentHabitId,
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

                    viewModel.addEditHabit(habit)
                    findNavController().navigate(R.id.action_editAddFragment_to_mainFragment)
                }
            }
        }
    }

    private fun init() {

        setColorPickerConfig()
        setSaveButtonConfig()
        binding.btnClose.setOnClickListener {
            findNavController().navigate(R.id.action_editAddFragment_to_mainFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            if (it.containsKey(Constants.KEY_EXTRA_EDIT_HABIT)) {

                val editHabit: Habit? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.getParcelable(Constants.KEY_EXTRA_EDIT_HABIT, Habit::class.java)
                } else {
                    @Suppress("DEPRECATION") it.getParcelable(Constants.KEY_EXTRA_EDIT_HABIT)
                }

                if (editHabit != null) {
                    viewModel.currentHabitId = editHabit.id
                    setEditHabitData(editHabit)
                }
                else
                    viewModel.currentHabitId = 0
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