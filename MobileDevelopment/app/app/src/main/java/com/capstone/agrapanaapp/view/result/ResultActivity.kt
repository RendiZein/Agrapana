package com.capstone.agrapanaapp.view.result

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.agrapanaapp.R

class ResultActivity : AppCompatActivity() {

    companion object {
        const val CAMERA_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
    }
}