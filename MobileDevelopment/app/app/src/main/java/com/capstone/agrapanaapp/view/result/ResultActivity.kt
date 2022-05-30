package com.capstone.agrapanaapp.view.result

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.capstone.agrapanaapp.R
import com.capstone.agrapanaapp.databinding.ActivityMainBinding
import com.capstone.agrapanaapp.databinding.ActivityResultBinding
import com.capstone.agrapanaapp.view.camera.CameraActivity.Companion.CAMERA_X_RESULT
import com.capstone.agrapanaapp.view.helper.rotateBitmap
import com.capstone.agrapanaapp.view.helper.uriToFile
import com.capstone.agrapanaapp.view.main.MainActivity
import com.google.android.gms.auth.api.identity.SaveAccountLinkingTokenRequest.EXTRA_TOKEN
import java.io.ByteArrayOutputStream
import java.io.File

class ResultActivity : AppCompatActivity() {



    private lateinit var currentPhotoPath: String
    private var getFile: File? = null
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentPhotoPath = intent.getStringExtra(EXTRA_PATH_IMAGE).toString()
        val result = rotateBitmap(
            BitmapFactory.decodeFile(currentPhotoPath),
            true
        )
        binding.resultImageView.setImageBitmap(result)

    }

    companion object {
        const val CAMERA_RESULT = 200
        const val EXTRA_PATH_IMAGE = "extra_path_IMAGE"
    }
}