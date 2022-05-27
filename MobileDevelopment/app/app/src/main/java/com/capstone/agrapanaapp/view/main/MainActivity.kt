package com.capstone.agrapanaapp.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.agrapanaapp.R
import com.capstone.agrapanaapp.view.authentication.login.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val i = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(i)
        supportActionBar?.hide()
    }
}