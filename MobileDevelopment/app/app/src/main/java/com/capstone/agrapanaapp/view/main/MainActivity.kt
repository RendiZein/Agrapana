package com.capstone.agrapanaapp.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import com.capstone.agrapanaapp.R
import com.capstone.agrapanaapp.databinding.ActivityMainBinding
import com.capstone.agrapanaapp.view.authentication.login.LoginActivity
import com.capstone.agrapanaapp.view.camera.CameraActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val i = Intent(this@MainActivity, LoginActivity::class.java)
//        startActivity(i)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnCamera.setOnClickListener {
            Log.d("TAG", "onCreate: button clicked")
            startActivity(Intent(this@MainActivity, CameraActivity::class.java))
        }

    }
}