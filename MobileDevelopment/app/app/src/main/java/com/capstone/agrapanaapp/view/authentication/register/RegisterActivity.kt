package com.capstone.agrapanaapp.view.authentication.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.agrapanaapp.R
import com.capstone.agrapanaapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}