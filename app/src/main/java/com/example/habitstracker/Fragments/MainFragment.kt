package com.example.habitstracker.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.habitstracker.Constants
import com.example.habitstracker.Habit
import com.example.habitstracker.R
import com.example.habitstracker.Adapters.ViewPaperAdapter
import com.example.habitstracker.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentMainBinding is null")


    private val tabName = arrayOf(
        Constants.KEY_HABIT_STATE_GOOD,
        Constants.KEY_HABIT_STATE_BAD
    )



    private val fragments = arrayOf(
        HabitsFragment.newInstance(Constants.KEY_HABIT_STATE_GOOD),
        HabitsFragment.newInstance(Constants.KEY_HABIT_STATE_BAD)
    )




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        init()


        return binding.root
    }


    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            if (it.containsKey(Constants.KEY_EXTRA_ADD_HABIT)) {
                val habit = it.getSerializable(Constants.KEY_EXTRA_ADD_HABIT) as? Habit ?: Habit()
                val bundle = Bundle()
                bundle.putSerializable(Constants.KEY_EXTRA_ADD_HABIT, habit)
                when (habit.type) {
                    Constants.KEY_HABIT_STATE_GOOD -> fragments[0].arguments = bundle
                    Constants.KEY_HABIT_STATE_BAD -> fragments[1].arguments = bundle
                    else -> return
                }
            } else if (it.containsKey(Constants.KEY_EXTRA_EDIT_HABIT)) {
                val habit = it.getSerializable(Constants.KEY_EXTRA_EDIT_HABIT) as? Habit ?: Habit()
                val bundle = Bundle()
                bundle.putSerializable(Constants.KEY_EXTRA_EDIT_HABIT, habit)
                when (habit.type) {
                    Constants.KEY_HABIT_STATE_GOOD -> fragments[0].arguments = bundle
                    Constants.KEY_HABIT_STATE_BAD -> fragments[1].arguments = bundle
                    else -> return
                }
            }
            it.clear()
        }

    }


    private fun init() {
        with(binding)
        {
            viewPager2.adapter = ViewPaperAdapter(
                fragments,
                childFragmentManager,
                lifecycle
            )

            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.text = tabName[position]
            }.attach()

            fabAddHabit.setOnClickListener {
                Navigation.findNavController(binding.root).navigate(R.id.editAddFragment)
            }

        }
    }
}