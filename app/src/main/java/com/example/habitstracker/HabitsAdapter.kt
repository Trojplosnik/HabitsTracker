package com.example.habitstracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstracker.databinding.RvItemHabitBinding
import java.util.Collections


class HabitsAdapter(
    private val habits: MutableList<Habit>,
    private val actionListener: IHabitActionListener
) : ListAdapter<Habit, HabitsAdapter.HabitsViewHolder>(ItemCallback) {

    object ItemCallback: DiffUtil.ItemCallback<Habit>() {
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
            actionListener.onClick(habit, position)
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
        habits.add(habit)
        notifyItemInserted(habits.indexOf(habit))
    }

    fun editHabit(habit: Habit, habitPos: Int) {
        habits[habitPos] = habit
        notifyItemChanged(habitPos)
    }

    fun swapHabits(firstHabitPos: Int, secondHabitPos: Int)
    {
        Collections.swap(habits, firstHabitPos, secondHabitPos)
        notifyItemMoved(firstHabitPos,secondHabitPos)
    }

    fun removeHabit(habitPos: Int) {
        habits.removeAt(habitPos)
        notifyItemRemoved(habitPos)
    }
}