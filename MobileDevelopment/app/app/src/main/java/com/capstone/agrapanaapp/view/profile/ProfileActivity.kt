package com.capstone.agrapanaapp.view.profile

import android.content.Context
import android.content.Intent
import android.content.Intent.EXTRA_EMAIL
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.agrapanaapp.R
import com.capstone.agrapanaapp.databinding.ActivityProfileBinding
import com.capstone.agrapanaapp.model.UserPreference
import com.capstone.agrapanaapp.view.authentication.login.LoginActivity
import com.capstone.agrapanaapp.view.helper.ViewModelFactory
import com.capstone.agrapanaapp.view.main.MainActivity
import com.capstone.agrapanaapp.view.main.MainViewModel

class ProfileActivity : AppCompatActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        profileViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore),this,dataStore)
        )[ProfileViewModel::class.java]
        setupAction()
        setupView()
    }

    private fun setupView() {
        profileViewModel.getEmail().observe(this){ email ->
            val username = when ("@") {
                in email -> {
                    val arr =email.split("@".toRegex(), 2).toTypedArray()
                    arr[0]
                }
                else -> {
                    email
                }
            }
            binding.tvName.text = username
            binding.tvEmail.text= email
        }



    }

    private fun setupAction() {
        binding.ivBack.setOnClickListener{
            finish()
        }
        binding.btnLogout.setOnClickListener {
            profileViewModel.logoutUser()
            val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        const val EXTRA_EMAIL = "extra_email"
        private const val TAG = "ProfileActivity"
    }
}