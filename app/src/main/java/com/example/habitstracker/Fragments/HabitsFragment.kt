package com.example.habitstracker.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstracker.Constants
import com.example.habitstracker.Habit
import com.example.habitstracker.Adapters.HabitsAdapter
import com.example.habitstracker.IHabitActionListener
import com.example.habitstracker.R
import com.example.habitstracker.databinding.FragmentHabitsBinding


class HabitsFragment : Fragment(), IHabitActionListener {

    private var _adapter: HabitsAdapter? = null
    private val adapter
        get() = _adapter ?: throw IllegalStateException("HabitsAdapter is null")



    private var _binding: FragmentHabitsBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentHabitsBinding is null")

    private val itemTouchHelper = ItemTouchHelper(object :
        ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT
        ) {

        override fun onMove(
            recyclerView: RecyclerView,
            source: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            adapter.swapHabits(source.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            adapter.removeHabit(position)
        }

    })


    override fun onClick(habit: Habit) {
        val bundle = Bundle()
        bundle.putSerializable(Constants.KEY_EXTRA_EDIT_HABIT, habit)
        Navigation.findNavController(binding.root).navigate(R.id.editAddFragment, bundle)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        @Suppress("DEPRECATION")
        arguments?.let {

                if (it.containsKey(Constants.KEY_EXTRA_ADD_HABIT)) {
                    adapter.addHabit(it.getSerializable(Constants.KEY_EXTRA_ADD_HABIT) as Habit)
                } else if (it.containsKey(Constants.KEY_EXTRA_EDIT_HABIT)) {
                    adapter.editHabit(it.getSerializable(Constants.KEY_EXTRA_EDIT_HABIT) as Habit)
                }
            it.clear()
            }
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
        }

        return binding.root
    }


    companion object {

        @JvmStatic
        fun newInstance(type: String) = HabitsFragment().apply {
            _adapter = when (type)
            {
                Constants.KEY_HABIT_STATE_GOOD ->  HabitsAdapter(type, Constants.habits, this)
                Constants.KEY_HABIT_STATE_BAD ->  HabitsAdapter(type, Constants.habits, this)
                else -> throw IllegalStateException("Wrong list type in adapter")
            }
        }
    }
}