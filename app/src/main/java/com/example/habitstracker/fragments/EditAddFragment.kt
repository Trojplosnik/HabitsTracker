package com.example.habitstracker.fragments


import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.children
import androidx.core.view.doOnLayout
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.habitstracker.Constants
import com.example.habitstracker.model.Habit
import com.example.habitstracker.R
import com.example.habitstracker.databinding.FragmentEditAddBinding
import com.example.habitstracker.model.HabitDatabase
import com.example.habitstracker.model.HabitsRepository
import com.example.habitstracker.viewModels.EditEddViewModel
import com.example.habitstracker.viewModels.factories.EditEddViewModelFactory
import kotlinx.coroutines.launch


class EditAddFragment : Fragment() {



    private var _binding: FragmentEditAddBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentAddEditBinding is null")

    private val viewModel: EditEddViewModel by viewModels<EditEddViewModel> {
        EditEddViewModelFactory(
            HabitsRepository(
                HabitDatabase.getDatabase(requireContext()).habitDao()
            )
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditAddBinding.inflate(inflater, container, false)


        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                    if (uiState.habitLoaded)
                    {
                        setEditHabitData(viewModel.getCurrentHabit())
                        viewModel.dismissState()
                    }
                    if (uiState.isSaving) {
                        if (uiState.showToast)
                        {
                            Toast.makeText(
                                activity,
                                R.string.toast_fill_fields,
                                Toast.LENGTH_LONG
                            ).show()
                            viewModel.dismissState()
                        }
                        else
                        {
                            viewModel.dismissState()
                            findNavController().navigate(R.id.action_editAddFragment_to_mainFragment)
                        }

                    }
                }
        }


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
                        viewModel.setColor(color)
                    }
                }
            }
        }
    }


    private fun setVMConfig() {
        with(binding) {
            etHabitName.doAfterTextChanged { viewModel.setName(it.toString()) }
            etHabitDesc.doAfterTextChanged { viewModel.setDescription(it.toString()) }
            etAmount.doAfterTextChanged { viewModel.setAmount(it.toString()) }
            etFrequency.doAfterTextChanged { viewModel.setFrequency(it.toString()) }
            etHabitName.doAfterTextChanged { viewModel.setName(it.toString()) }

            rGrpType.setOnCheckedChangeListener { _, _ ->
                if (rBtnBad.isChecked) viewModel.setType(rBtnBad.text.toString())
                else if (rBtnGood.isChecked) viewModel.setType(rBtnGood.text.toString())
            }


            spnHabitPriority.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.setPriority(spnHabitPriority.selectedItem.toString())
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    viewModel.setPriority("Not selected")
                }
            }


        }
    }

    private fun init() {

        setColorPickerConfig()
        setVMConfig()

        with(binding) {
            btnSave.setOnClickListener {
                viewModel.sendToDataBase()
            }


            btnClose.setOnClickListener {
                findNavController().navigate(R.id.action_editAddFragment_to_mainFragment)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            if (it.containsKey(Constants.KEY_EXTRA_EDIT_HABIT)) {
                val habitId = it.getInt(Constants.KEY_EXTRA_EDIT_HABIT, 0)

                if (habitId > 0) {
                    viewModel.getHabitById(habitId)
                }
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