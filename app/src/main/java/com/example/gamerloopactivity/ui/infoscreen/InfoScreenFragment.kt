package com.example.gamerloopactivity.ui.infoscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gamerloopactivity.databinding.FragmentInfoScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoScreenFragment : Fragment() {

    private var _binding: FragmentInfoScreenBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentInfoScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

}