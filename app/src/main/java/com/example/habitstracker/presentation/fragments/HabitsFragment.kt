package com.example.habitstracker.presentation.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstracker.R
import com.example.habitstracker.databinding.FragmentHabitsBinding
import com.example.habitstracker.domain.Constants.KEY_EXTRA_EDIT_HABIT
import com.example.habitstracker.domain.entities.Habit
import com.example.habitstracker.presentation.adapters.HabitsAdapter
import com.example.habitstracker.domain.entities.Type
import com.example.habitstracker.presentation.viewModels.HabitsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HabitsFragment : Fragment() {

    private val adapter by lazy { HabitsAdapter(this::navigateToEditAdd, this::onHabitDone) }


    private val viewModel: HabitsListViewModel by activityViewModels()

    private var type = Type.NOT_SELECTED


    private var _binding: FragmentHabitsBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentHabitsBinding is null")


    private val itemTouchHelper = ItemTouchHelper(object :
        ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.START
        ) {

        override fun onMove(
            recyclerView: RecyclerView,
            source: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.deleteHabit(adapter.getHabit(viewHolder.adapterPosition))
        }
    })


    private fun navigateToEditAdd(habitId: Int) {
        val bundle = Bundle()
        bundle.putInt(KEY_EXTRA_EDIT_HABIT, habitId)
        findNavController().navigate(R.id.action_mainFragment_to_editAddFragment, bundle)
    }

    private fun onHabitDone(habit: Habit) {
        viewModel.doneHabit(habit)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHabitsBinding.inflate(inflater, container, false)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                if (uiState.showToast){
                    val toastMsg = when (uiState.toastState) {
                        HabitsListViewModel.ToastState.GOOD_LESS -> "It's worth doing it ${uiState.leftToDo} more times"
                        HabitsListViewModel.ToastState.BAD_LESS -> "You can do it ${uiState.leftToDo} more times"
                        HabitsListViewModel.ToastState.GOOD_MORE -> "You are breathtaking!"
                        HabitsListViewModel.ToastState.BAD_MORE -> "Stop doing this!"
                    }
                    Toast.makeText(activity, toastMsg, Toast.LENGTH_LONG).show()
                    viewModel.dismissState()
                }
            }
        }



        with(binding) {
            rvHabitsRecycler.layoutManager = LinearLayoutManager(context)
            rvHabitsRecycler.adapter = adapter
            itemTouchHelper.attachToRecyclerView(rvHabitsRecycler)


            viewModel.habits.observe(requireActivity()) { list ->
                adapter.submitList(list.filter { habit: Habit -> habit.type == type })
            }
        }

        return binding.root
    }


    companion object {

        @JvmStatic
        fun newInstance(type: Type) = HabitsFragment().apply {
            this.type = type
        }
    }
}