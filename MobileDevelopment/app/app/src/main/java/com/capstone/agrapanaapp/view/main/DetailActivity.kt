package com.capstone.agrapanaapp.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.capstone.agrapanaapp.R
import com.capstone.agrapanaapp.databinding.ActivityDetailBinding
import com.capstone.agrapanaapp.databinding.ActivityMainBinding
import com.capstone.agrapanaapp.model.Fruit

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupData()
    }

    private fun setupData() {
        val fruit = intent.getParcelableExtra<Fruit>("Fruit") as Fruit
        binding.profileImageView.setImageResource(fruit.photo)
        binding.nameTextView.text = fruit.name
        binding.descTextView.text = fruit.description
    }
}