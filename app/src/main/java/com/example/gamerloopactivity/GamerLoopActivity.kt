package com.example.gamerloopactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gamerloopactivity.databinding.ActivityGamerLoopBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GamerLoopActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGamerLoopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamerLoopBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}