package com.example.habitstracker.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstracker.Constants
import com.example.habitstracker.adapters.HabitsAdapter
import com.example.habitstracker.interfaces.IHabitActionListener
import com.example.habitstracker.R
import com.example.habitstracker.databinding.FragmentHabitsBinding
import com.example.habitstracker.viewModels.HabitsListViewModel


class HabitsFragment : Fragment(), IHabitActionListener {

    private var _adapter: HabitsAdapter? = null
    private val adapter
        get() = _adapter ?: throw IllegalStateException("HabitsAdapter is null")

    private var _viewModel: HabitsListViewModel? = null
    private val viewModel
        get() = _viewModel ?: throw IllegalStateException("HabitsListViewModel is null")



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
            val sourcePos = source.adapterPosition
            val targetPos = target.adapterPosition


            viewModel.swapHabits(adapter.allHabits.indexOf(adapter.habits[sourcePos]),
                adapter.allHabits.indexOf(adapter.habits[targetPos]))
            adapter.notifyItemMoved(source.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            viewModel.removeHabit(adapter.allHabits.indexOf(adapter.habits[position]))
            adapter.notifyItemRemoved(position)
        }
    })


    override fun onClick(position: Int) {
        val bundle = Bundle()
        bundle.putInt(Constants.KEY_EXTRA_EDIT_HABIT, position)
        findNavController().navigate(R.id.editAddFragment, bundle)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = ViewModelProvider(requireActivity())[HabitsListViewModel::class.java]
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.habits.observe(requireActivity()) {
            adapter.allHabits = it
            adapter.notifyDataSetChanged()}
    }

    companion object {

        @JvmStatic
        fun newInstance(type: String) = HabitsFragment().apply {
            _adapter = HabitsAdapter(type, listOf(), this)
        }
    }
}