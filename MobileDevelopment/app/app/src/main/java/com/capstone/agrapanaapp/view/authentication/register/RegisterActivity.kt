package com.capstone.agrapanaapp.view.authentication.register

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.capstone.agrapanaapp.R
import com.capstone.agrapanaapp.databinding.ActivityRegisterBinding
import com.capstone.agrapanaapp.view.authentication.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        setupView()
        setupAction()
        registerViewModel.isLoading.observe(this) { showLoading(it) }
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

    private fun setupAction() {
        binding.btnLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.btnRegister.setOnClickListener {
            val name = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPass.text.toString()
            when {
                name.isEmpty() -> {
                    binding.edtName.error = getString(R.string.input_name)
                }
                email.isEmpty() -> {
                    binding.edtEmail.error = getString(R.string.input_email)
                }
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    binding.edtEmail.error = getString(R.string.email_pattern_alert)}
                password.isEmpty() -> {
                    binding.edtPass.error = getString(R.string.input_password)
                }
                password.length < 6 -> {
                    binding.edtPass.error = getString(R.string.password_number_alert)
                }
//                else -> {
//                    signupViewModel.register(
//                        name,email, password
//                    )
//                    signupViewModel.success.observe(this){ success ->
//                        when {
//                            success == YES ->
//                                AlertDialog.Builder(this).apply {
//                                    setTitle(R.string.success_register_alert)
//                                    setMessage(getString(R.string.success_register))
//                                    setPositiveButton(getString(R.string.alert_continue)) { _, _ ->
//                                        val intent = Intent(context, LoginActivity::class.java)
//                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                                        startActivity(intent)
//                                        finish()
//                                    }
//
//                                    create()
//                                    show()
//                                }
//                            success == TAKEN ->{
//                                Toast.makeText(this, R.string.fail_taken, Toast.LENGTH_SHORT).show()}
//                            success == NO ->{
//                                Toast.makeText(this, getString(R.string.fail_register), Toast.LENGTH_SHORT).show()}
//                        }
//                    }
//
//                }
            }
        }
    }

    private fun showLoading(it: Boolean) {
        binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }
}