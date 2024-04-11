package com.example.habitstracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.habitstracker.databinding.FragmentBottomSheetBinding

import com.example.habitstracker.viewModels.HabitsListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _viewModel: HabitsListViewModel? = null
    private val viewModel
        get() = _viewModel ?: throw IllegalStateException("HabitsListViewModel is null")


    private var _binding: FragmentBottomSheetBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentBottomSheetBinding is null")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = ViewModelProvider(requireActivity())[HabitsListViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        with(binding){
            vArrowUp.setOnClickListener { viewModel.sortList() }
            vArrowDown.setOnClickListener { viewModel.reverseSortList() }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    dismiss()
                    viewModel.removeFilter()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.filterList(newText)
                    return true
                }

            })
        }
    }
}