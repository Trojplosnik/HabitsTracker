package com.example.habitstracker.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstracker.Habit
import com.example.habitstracker.IHabitActionListener
import com.example.habitstracker.databinding.RvItemHabitBinding
import java.util.Collections


class HabitsAdapter(
    private val type: String,
    private val _habits: MutableList<Habit>,
    private val actionListener: IHabitActionListener
) : ListAdapter<Habit, HabitsAdapter.HabitsViewHolder>(ItemCallback) {


    private val habits
        get() = _habits.filter { it.type == type }


    object ItemCallback : DiffUtil.ItemCallback<Habit>() {
        override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
            return oldItem == newItem
        }
    }

    class HabitsViewHolder(
        val binding: RvItemHabitBinding
    ) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemHabitBinding.inflate(inflater, parent, false)


        return HabitsViewHolder(binding)
    }

    override fun getItemCount(): Int = habits.size

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) {
        val habit = habits[position]



        with(holder.binding) {
            root.setOnClickListener {
                actionListener.onClick(habit)
            }
            with(habit) {
                tvName.text = name
                tvDescription.text = description
                tvAmount.text = amount
                tvFrequency.text = frequency
                tvType.text = type
                tvPriority.text = priority
                cvHabit.setCardBackgroundColor(color)
            }
        }
    }


    fun addHabit(habit: Habit) {
        _habits.add(habit)
        notifyItemInserted(_habits.indexOf(habit))
    }

    fun editHabit(habit: Habit) {
        _habits[_habits.indexOf(habit)] = habit
        notifyItemChanged(_habits.indexOf(habit))
    }

    fun swapHabits(firstHabitPos: Int, secondHabitPos: Int) {
        val trueFirstHabitPos = habits.indexOf(habits[firstHabitPos])
        val  trueSecondHabitPos = habits.indexOf(habits[secondHabitPos])
        Collections.swap(habits, trueFirstHabitPos, trueSecondHabitPos)
        notifyItemMoved(trueFirstHabitPos, trueSecondHabitPos)
    }

    fun removeHabit(habitPos: Int) {
        _habits.remove(habits[habitPos])
        notifyItemRemoved(habits.indexOf(habits[habitPos]))
    }
}