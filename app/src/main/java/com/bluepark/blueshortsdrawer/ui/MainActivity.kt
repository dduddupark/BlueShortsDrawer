package com.bluepark.blueshortsdrawer.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bluepark.blueshortsdrawer.databinding.LayoutMainBinding

class MainActivity: AppCompatActivity() {

    private lateinit var binding: LayoutMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}