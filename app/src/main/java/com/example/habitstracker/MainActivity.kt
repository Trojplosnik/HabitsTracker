package com.example.habitstracker

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstracker.databinding.ActivityMainBinding
import java.lang.IllegalStateException


class MainActivity : AppCompatActivity(), IHabitActionListener {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("ActivityMainBinding is null")


    private val adapter = HabitsAdapter(mutableListOf(), this)

    private var editHabitPosition = 0

    @Suppress("DEPRECATION")
    private val editAddLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Constants.RESULT_CODE_ADD_HABIT) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    adapter.addHabit(it.data?.getSerializableExtra(Constants.KEY_EXTRA_ADD_HABIT, Habit::class.java) ?: Habit())
                }
                else  adapter.addHabit(it.data?.getSerializableExtra(Constants.KEY_EXTRA_ADD_HABIT) as Habit)
            }
            else if (it.resultCode == Constants.RESULT_CODE_EDIT_HABIT) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    adapter.editHabit(it.data?.getSerializableExtra(Constants.KEY_EXTRA_ADD_HABIT,
                        Habit::class.java) ?: Habit(), editHabitPosition)
                }
                else adapter.editHabit(it.data?.getSerializableExtra(Constants.KEY_EXTRA_ADD_HABIT) as Habit,
                    editHabitPosition)
            }
        }


    private val itemTouchHelper = ItemTouchHelper(object :
        ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){

        override fun onMove(recyclerView: RecyclerView,
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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }


    private fun init() {

        with(binding)
        {
            fabAddHabit.setOnClickListener {
                editAddLauncher.launch(Intent(this@MainActivity,
                    EditAddActivity::class.java))
            }

            rvHabitsRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
            rvHabitsRecycler.adapter = adapter
            itemTouchHelper.attachToRecyclerView(rvHabitsRecycler)
        }
    }

    override fun onClick(habit: Habit, position: Int) {
        editAddLauncher.launch(Intent(this@MainActivity,
            EditAddActivity::class.java).putExtra(Constants.KEY_EXTRA_EDIT_HABIT, habit))
        editHabitPosition = position
    }
}
