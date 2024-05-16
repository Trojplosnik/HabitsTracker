package com.example.habitstracker.presentation.fragments


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
import com.example.habitstracker.domain.Constants
import com.example.habitstracker.R
import com.example.habitstracker.data.database.HabitDatabase
import com.example.habitstracker.data.remote.IHabitService
import com.example.habitstracker.data.remote.RemoteDataSource
import com.example.habitstracker.data.repositories.HabitsRepositoryImpl
import com.example.habitstracker.domain.adapters.HabitsAdapter
import com.example.habitstracker.databinding.FragmentHabitsBinding
import com.example.habitstracker.domain.entities.Habit
import com.example.habitstracker.domain.entities.Type
import com.example.habitstracker.domain.repositorys.IHabitsRepository
import com.example.habitstracker.presentation.viewModels.HabitsListViewModel
import com.example.habitstracker.presentation.viewModels.factories.HabitsListViewModelFactory

import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HabitsFragment : Fragment() {

    private val adapter by lazy { HabitsAdapter(this::navigateToEditAdd) }




    val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }).build()

    private val HabitService = Retrofit.Builder()
        .baseUrl("https://droid-test-server.doubletapp.ru/api/")
        .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
        .build().create(IHabitService::class.java)


    private val viewModel: HabitsListViewModel by activityViewModels {
        HabitsListViewModelFactory(HabitsRepositoryImpl(RemoteDataSource(HabitService),
            HabitDatabase.getDatabase(requireContext()).habitDao()))
    }

    private var type = Type.NOT_SELECTED


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
        fun newInstance(type: Type) = HabitsFragment().apply {
            this.type = type
        }
    }
}