package com.capstone.agrapanaapp.view.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.agrapanaapp.R
import com.capstone.agrapanaapp.databinding.ActivityMainBinding
import com.capstone.agrapanaapp.model.Fruit
import com.capstone.agrapanaapp.view.authentication.login.LoginActivity
import com.capstone.agrapanaapp.view.camera.CameraActivity
import com.capstone.agrapanaapp.view.helper.rotateBitmap
import com.capstone.agrapanaapp.view.helper.uriToFile
import com.capstone.agrapanaapp.view.result.ResultActivity
import java.io.ByteArrayOutputStream
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val list = java.util.ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(1).isEnabled = false

        binding.rvFruits.setHasFixedSize(true)

        list.addAll(listFruit)
        showRecyclerList()

        binding.fab.setOnClickListener {
            startActivity(Intent(this@MainActivity, CameraActivity::class.java))
        }

        binding.testLogin.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }
    }

    private fun showRecyclerList() {
//        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            rvHeroes.layoutManager = GridLayoutManager(this, 2)
//        } else {
//            rvHeroes.layoutManager = LinearLayoutManager(this)
//        }
        val ko = LinearLayoutManager(this)
        ko.orientation = RecyclerView.HORIZONTAL

        binding.rvFruits.layoutManager = ko
        val listHeroAdapter = FruitAdapter(list)
        binding.rvFruits.adapter = listHeroAdapter

    }

    private val listFruit: ArrayList<Fruit>
        get() {
            val dataName = resources.getStringArray(R.array.data_name)
            val dataDescription = resources.getStringArray(R.array.data_description)
            val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
            val listFruit = ArrayList<Fruit>()
            for (i in dataName.indices) {
                val hero = Fruit(dataName[i],dataDescription[i], dataPhoto.getResourceId(i, -1))
                listFruit.add(hero)
            }
            return listFruit
        }
}