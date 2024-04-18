package com.example.habitstracker.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.habitstracker.databinding.RvItemHabitBinding
import com.example.habitstracker.model.Habit
import com.example.habitstracker.interfaces.IHabitActionListener


class HabitsAdapter(
    private val actionListener: IHabitActionListener
) : ListAdapter<Habit, HabitsAdapter.HabitsViewHolder>(ItemCallback) {


    object ItemCallback : DiffUtil.ItemCallback<Habit>() {
        override fun areItemsTheSame(oldItem: Habit, newItem: Habit): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Habit, newItem: Habit): Boolean {
            return oldItem == newItem
        }
    }

    class HabitsViewHolder(
        val binding: RvItemHabitBinding
    ) : RecyclerView.ViewHolder(binding.root)




    fun getHabit(position: Int): Habit = currentList[position]



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemHabitBinding.inflate(inflater, parent, false)
        return HabitsViewHolder(binding)
    }

    override fun getItemCount(): Int = currentList.size

    override fun onBindViewHolder(holder: HabitsViewHolder, position: Int) {

        val habit = currentList[position]



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
}
