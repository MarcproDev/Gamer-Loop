package com.example.gamerloopactivity.ui.contentscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gamerloopactivity.databinding.FragmentContentscreenBinding

class ContentScreenFragment : Fragment() {

    private var _binding: FragmentContentscreenBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentscreenBinding.inflate(layoutInflater, container, false)
        return binding.root

    }


}