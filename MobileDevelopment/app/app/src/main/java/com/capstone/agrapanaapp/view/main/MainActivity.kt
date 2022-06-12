package com.capstone.agrapanaapp.view.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.get
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
//import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.agrapanaapp.R
import com.capstone.agrapanaapp.databinding.ActivityMainBinding
import com.capstone.agrapanaapp.model.Fruit
import com.capstone.agrapanaapp.model.UserPreference
import com.capstone.agrapanaapp.view.authentication.login.LoginActivity
import com.capstone.agrapanaapp.view.authentication.login.LoginViewModel
import com.capstone.agrapanaapp.view.camera.CameraActivity
import com.capstone.agrapanaapp.view.helper.ViewModelFactory
import com.capstone.agrapanaapp.view.helper.rotateBitmap
import com.capstone.agrapanaapp.view.helper.uriToFile
import com.capstone.agrapanaapp.view.profile.ProfileActivity
import com.capstone.agrapanaapp.view.result.ResultActivity
import java.io.ByteArrayOutputStream
import java.io.File



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val list = java.util.ArrayList<Fruit>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(1).isEnabled = false
        binding.bottomNavigationView.menu.get(2).setOnMenuItemClickListener {
            val mFragmentManager = supportFragmentManager
            val mHistoryFragment = HistoryFragment()
            val fragment = mFragmentManager.findFragmentByTag(HistoryFragment::class.java.simpleName)

            if (fragment !is HistoryFragment) {
                Log.d("MyFlexibleFragment", "Fragment Name :" + HistoryFragment::class.java.simpleName)
                mFragmentManager
                    .beginTransaction()
                    .add(R.id.frame_container, mHistoryFragment, HistoryFragment::class.java.simpleName)
                    .commit()
            }
            true
        }
        binding.rvFruits.setHasFixedSize(true)
        setUpAction()
        list.addAll(listFruit)
        showRecyclerList()

    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore),this,dataStore)
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun setUpAction() {
        mainViewModel.getUser().observe(this) {

                binding.fab.setOnClickListener {
                    startActivity(Intent(this@MainActivity, CameraActivity::class.java))
                }
                binding.ivProfile.setOnClickListener{
                    val i = Intent(this@MainActivity, ProfileActivity::class.java)
                    startActivity(i)
                }
                mainViewModel.getEmail().observe(this){ name ->
                    val username = when ("@") {
                        in name -> {
                            val arr = name.split("@".toRegex(), 2).toTypedArray()
                            arr[0]
                        }
                        else -> {
                            name
                        }
                    }
                    binding.tvName.text = username
                }

        }
    }

    private fun showRecyclerList() {
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