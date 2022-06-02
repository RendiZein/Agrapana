package com.capstone.agrapanaapp.view.result

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.agrapanaapp.R
import com.capstone.agrapanaapp.data.remote.retrofit.ApiConfig
import com.capstone.agrapanaapp.databinding.ActivityMainBinding
import com.capstone.agrapanaapp.databinding.ActivityResultBinding
import com.capstone.agrapanaapp.model.FileUploadResponse
import com.capstone.agrapanaapp.model.UserPreference
import com.capstone.agrapanaapp.view.camera.CameraActivity.Companion.CAMERA_X_RESULT
import com.capstone.agrapanaapp.view.helper.ViewModelFactory
import com.capstone.agrapanaapp.view.helper.reduceFileImage
import com.capstone.agrapanaapp.view.helper.rotateBitmap
import com.capstone.agrapanaapp.view.helper.uriToFile
import com.capstone.agrapanaapp.view.main.MainActivity
import com.google.android.gms.auth.api.identity.SaveAccountLinkingTokenRequest.EXTRA_TOKEN
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
//import java.util.prefs.Preferences

class ResultActivity : AppCompatActivity() {

    companion object {
        const val CAMERA_RESULT = 200
        const val EXTRA_PATH_IMAGE = "extra_path_IMAGE"
        const val EXTRA_URI = "extra_uri"
    }

    private lateinit var currentPhotoPath: String
    private var getFile: File? = null
    private lateinit var binding: ActivityResultBinding
    private lateinit var resultViewModel: ResultViewModel

    internal val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        currentPhotoPath = intent.getStringExtra(EXTRA_PATH_IMAGE).toString()

        val result = rotateBitmap(
            BitmapFactory.decodeFile(currentPhotoPath),
            true
        )
//        val bytes = ByteArrayOutputStream()
//        result.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(this@ResultActivity.contentResolver, result,"Title",null).toString()
        val uri = Uri.parse(path)
//        val uriString = intent.getStringExtra("extra_uri").toString()
//        val uri = Uri.parse(uriString)
        getFile = uriToFile(uri,this)
        binding.resultImageView.setImageBitmap(result)

        setupViewModel()
        binding.uploadButton.setOnClickListener{uploadImage()}
    }

    private fun uploadImage() {
        binding.progressBar.visibility =  View.VISIBLE
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )
            val description = "HAAAAAAAA".toRequestBody("text/plain".toMediaType())

                resultViewModel.getUser().observe(this) { user ->
                val service = ApiConfig.getApiService().uploadImage(imageMultipart,description,"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLWp6X0RNYjliUjZocHVJS00iLCJpYXQiOjE2NTM5NTczODN9.YqtUhxz2XTG4TgsCFvIF3T8cix4oHWVj9J53l-YOjIA")
                service.enqueue(object : Callback<FileUploadResponse> {
                    override fun onResponse(
                        call: Call<FileUploadResponse>,
                        response: Response<FileUploadResponse>
                    ) {
                        if (response.isSuccessful) {
                            binding.progressBar.visibility =  View.GONE
                            val responseBody = response.body()
                            if (responseBody != null && !responseBody.error) {
                                Toast.makeText(
                                    this@ResultActivity,
                                    responseBody.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            binding.progressBar.visibility =  View.GONE
                            Toast.makeText(this@ResultActivity, response.message(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                        binding.progressBar.visibility =  View.GONE
                        Toast.makeText(this@ResultActivity, getString(R.string.retrofit_failure), Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            }

        } else {
            binding.progressBar.visibility =  View.GONE
            Toast.makeText(this@ResultActivity, getString(R.string.please_insert_image), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupViewModel() {
        resultViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore),this,dataStore)
        )[ResultViewModel::class.java]

    }
}