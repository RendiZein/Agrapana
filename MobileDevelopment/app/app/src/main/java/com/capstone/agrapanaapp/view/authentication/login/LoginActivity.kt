package com.capstone.agrapanaapp.view.authentication.login

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.agrapanaapp.R
import com.capstone.agrapanaapp.databinding.ActivityLoginBinding
import com.capstone.agrapanaapp.model.UserModel
import com.capstone.agrapanaapp.model.UserPreference
import com.capstone.agrapanaapp.view.authentication.register.RegisterActivity
import com.capstone.agrapanaapp.view.helper.ViewModelFactory
import com.capstone.agrapanaapp.view.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    companion object{
        private const val YES = "yes"
        private const val NO = "no"
        private const val UNAUTHORIZED = "unauthorized"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        }

        setupView()
        setupViewModel()
        setupAction()
        loginViewModel.isLoading.observe(this) { showLoading(it) }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore),this,dataStore)
        )[LoginViewModel::class.java]
    }

    private fun setupAction() {
        binding.btnRegister.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
//        binding.btnLogin.setOnClickListener {
//            val email = binding.edtEmail.text.toString()
//            val password = binding.edtPass.text.toString()
//            when {
//                // ini kalo email/password kosong, nampilin eror
//                email.isEmpty() -> {
//                    binding.edtEmail.error = getString(R.string.input_email)
//                }
//                password.isEmpty() -> {
//                    binding.edtPass.error = getString(R.string.input_password)
//                }
//                password.length < 6 -> {
//                    binding.edtPass.error = getString(R.string.password_number_alert)
//                }
//                else -> {
//                    loginViewModel.login(email, password)
//                    loginViewModel.userdata.observe(this){user ->
//                        saveUserdata(user.name,user.token)
//                    }
//                    loginViewModel.response.observe(this){ response ->
//                        when {
//                            response == YES ->{
//
//                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                                startActivity(intent)
//                                finish()
//
//                            }
//
//                            response == UNAUTHORIZED ->{
//                                Toast.makeText(this, R.string.fail_unauthorized, Toast.LENGTH_SHORT).show()}
//                            response == NO ->{
//                                Toast.makeText(this, getString(R.string.fail_login), Toast.LENGTH_SHORT).show()}
//                        }
//                    }
//                }
//            }
//        }
    }

    private fun showLoading(it: Boolean) {
        binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }

    private fun saveUserdata(name: String, token : String) {
        loginViewModel.loginData(UserModel(name,token,true))
    }

}