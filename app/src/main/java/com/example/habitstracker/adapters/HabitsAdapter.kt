package com.example.habitstracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstracker.model.Habit
import com.example.habitstracker.interfaces.IHabitActionListener
import com.example.habitstracker.databinding.RvItemHabitBinding


class HabitsAdapter(
    private val type: String,
    var allHabits: List<Habit>,
    private val actionListener: IHabitActionListener
) : ListAdapter<Habit, HabitsAdapter.HabitsViewHolder>(ItemCallback) {


    val habits
        get() = allHabits.filter { it.type == type }




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

        holder.binding.habit = habits[position]
        holder.binding.root.setOnClickListener {actionListener.onClick(allHabits.indexOf(habits[position]))}
    }
}