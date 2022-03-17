package com.hasannagizade.movielistapp.presentation.tabs.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hasannagizade.movielistapp.R
import com.hasannagizade.movielistapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}