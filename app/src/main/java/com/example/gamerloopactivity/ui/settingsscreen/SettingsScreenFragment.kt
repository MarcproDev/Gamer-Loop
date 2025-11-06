package com.example.gamerloopactivity.ui.settingsscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gamerloopactivity.databinding.FragmentSettingsScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsScreenFragment : Fragment() {

    private var _binding: FragmentSettingsScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingsScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

}