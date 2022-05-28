package com.capstone.agrapanaapp.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import com.capstone.agrapanaapp.R
import com.capstone.agrapanaapp.databinding.ActivityMainBinding
import com.capstone.agrapanaapp.view.authentication.login.LoginActivity
import com.capstone.agrapanaapp.view.camera.CameraActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val i = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(i)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.btnCamera.setOnClickListener {
            startActivity(Intent(this@MainActivity, CameraActivity::class.java))
        }

    }
}