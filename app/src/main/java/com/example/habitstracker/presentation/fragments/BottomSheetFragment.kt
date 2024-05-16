package com.example.habitstracker.presentation.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import com.example.habitstracker.databinding.FragmentBottomSheetBinding
import com.example.habitstracker.data.database.HabitDatabase
import com.example.habitstracker.data.remote.IHabitService
import com.example.habitstracker.data.remote.RemoteDataSource
import com.example.habitstracker.data.repositories.HabitsRepositoryImpl

import com.example.habitstracker.presentation.viewModels.HabitsListViewModel
import com.example.habitstracker.presentation.viewModels.factories.HabitsListViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class BottomSheetFragment : BottomSheetDialogFragment() {

    val okHttpClient = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }).build()

    private val HabitService = Retrofit.Builder()
        .baseUrl("https://droid-test-server.doubletapp.ru/api/")
        .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
        .build().create(IHabitService::class.java)


    private val viewModel: HabitsListViewModel by activityViewModels {
        HabitsListViewModelFactory(HabitsRepositoryImpl(
            RemoteDataSource(HabitService),
            HabitDatabase.getDatabase(requireContext()).habitDao()))
    }


    private var _binding: FragmentBottomSheetBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentBottomSheetBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)


        init()
        return binding.root
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        viewModel.setHabits()
    }

    private fun init() {
        with(binding){

            vArrowUp.setOnClickListener { viewModel.sortDatabase(true) }

            vArrowDown.setOnClickListener { viewModel.sortDatabase() }


            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.searchDatabase(newText)
                    return true
                }
            })
        }
    }
}