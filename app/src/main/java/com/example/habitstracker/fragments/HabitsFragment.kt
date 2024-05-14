package com.example.habitstracker.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstracker.Constants
import com.example.habitstracker.R
import com.example.habitstracker.adapters.HabitsAdapter
import com.example.habitstracker.databinding.FragmentHabitsBinding
import com.example.habitstracker.model.Habit
import com.example.habitstracker.model.HabitDatabase
import com.example.habitstracker.model.HabitsRepository
import com.example.habitstracker.viewModels.HabitsListViewModel
import com.example.habitstracker.viewModels.factories.HabitsListViewModelFactory


class HabitsFragment : Fragment() {

    private val adapter by lazy { HabitsAdapter(this::navigateToEditAdd) }

    private val viewModel: HabitsListViewModel by activityViewModels {
        HabitsListViewModelFactory(HabitsRepository(HabitDatabase.getDatabase(requireContext()).habitDao()))
    }

    private var type = ""


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


    private fun navigateToEditAdd(habitId: Int){
        val bundle = Bundle()
        bundle.putInt(Constants.KEY_EXTRA_EDIT_HABIT, habitId)
        findNavController().navigate(R.id.action_mainFragment_to_editAddFragment, bundle)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHabitsBinding.inflate(inflater, container, false)


        with(binding) {
            rvHabitsRecycler.layoutManager = LinearLayoutManager(context)
            rvHabitsRecycler.adapter = adapter
            itemTouchHelper.attachToRecyclerView(rvHabitsRecycler)


            viewModel.habits.observe(requireActivity()) { list ->
                adapter.submitList(list.filter { habit: Habit -> habit.type == type })
            }

            viewModel.habits.observe(requireActivity()) { list ->
                adapter.submitList(list.filter { habit: Habit -> habit.type == type })
            }
        }

        return binding.root
    }


    companion object {

        @JvmStatic
        fun newInstance(type: String) = HabitsFragment().apply {
            this.type = type
        }
    }
}