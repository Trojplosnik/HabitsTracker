package com.example.habitstracker.presentation.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.habitstracker.presentation.Constants
import com.example.habitstracker.R
import com.example.habitstracker.presentation.adapters.ViewPaperAdapter
import com.example.habitstracker.databinding.FragmentMainBinding
import com.example.habitstracker.domain.entities.Type
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {


    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentMainBinding is null")

    private val tabName = arrayOf(
        Constants.KEY_HABIT_STATE_GOOD,
        Constants.KEY_HABIT_STATE_BAD
    )


    private val fragments = arrayOf(
        HabitsFragment.newInstance(Type.GOOD),
        HabitsFragment.newInstance(Type.BAD)
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        init()

        return binding.root
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
                findNavController().navigate(R.id.action_mainFragment_to_editAddFragment)
            }

            tvBottomSheet.setOnClickListener {
                BottomSheetFragment().show(childFragmentManager, "BottomSheetTag")
            }

        }
    }
}