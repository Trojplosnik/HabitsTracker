package com.example.habitstracker.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.habitstracker.databinding.FragmentInfoBinding


class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("FragmentInfoBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }
}