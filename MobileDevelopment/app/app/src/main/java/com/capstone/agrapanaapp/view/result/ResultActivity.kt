package com.capstone.agrapanaapp.view.result

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ImageUtils
import com.capstone.agrapanaapp.R
import com.capstone.agrapanaapp.data.remote.retrofit.ApiConfig
import com.capstone.agrapanaapp.databinding.ActivityResultBinding
import com.capstone.agrapanaapp.model.FileUploadResponse
import com.capstone.agrapanaapp.model.UserPreference
import com.capstone.agrapanaapp.view.camera.CameraActivity
import com.capstone.agrapanaapp.view.helper.*
import com.capstone.agrapanaapp.view.main.MainActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


//import java.util.prefs.Preferences

class ResultActivity : AppCompatActivity() {

    companion object {
        const val CAMERA_RESULT = 200
        const val EXTRA_PATH_IMAGE = "extra_path_IMAGE"
        const val EXTRA_PATH_IMAGE_GALLERY = "extra_path_IMAGE_GALLERY"
        const val EXTRA_URI = "extra_uri"
    }

    private var getFile: File? = null
    private lateinit var binding: ActivityResultBinding
    private lateinit var resultViewModel: ResultViewModel

    internal val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val currentPhotoPath = intent.getStringExtra("extra_path_IMAGE")
        val galleryPath = intent.getStringExtra("extra_path_IMAGE_GALLERY")

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this@ResultActivity, MainActivity::class.java))
        }


        val result : Bitmap =
            if (currentPhotoPath==null) {rotateBitmap(
                BitmapFactory.decodeFile(galleryPath.toString()),
                false)}

        else {rotateBitmap(
            BitmapFactory.decodeFile(currentPhotoPath.toString()),
            true)}

        Log.d("TAG", "onCreate: $currentPhotoPath \n $galleryPath")

//        val result = rotateBitmap(
//            BitmapFactory.decodeFile(currentPhotoPath),
//            true)

//        val path2: String = this.filesDir.absolutePath
//        Log.d("TAG", "onCreate3: $path2")
//        ImageUtils.save(result, path2, Bitmap.CompressFormat.PNG);
//        val bytes = ByteArrayOutputStream()
//        result.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

//        val uri2 = UriUtils.file2Uri(FileUtils.getFileByPath(path2))

//        val path6 = MediaStore.Images.Media.insertImage(this@ResultActivity.contentResolver, result,"Title",null)
//        Log.d("TAG", "onCreate4: $path6")
//        val uri = Uri.parse(path6)

//        val uriString = intent.getStringExtra("extra_uri").toString()
//        val uri = Uri.parse(uriString)

        /* usaha 1*/
        val photoFile = createFile(application)
        try {
            FileOutputStream(photoFile).use { out ->
                result.compress(Bitmap.CompressFormat.PNG, 100, out) // bmp is your Bitmap instance
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        /* end of usaha 1*/
        getFile = photoFile

//        getFile = uriToFile(uri,this)
        binding.resultImageView.setImageBitmap(result)

        setupViewModel()
        binding.uploadButton.setOnClickListener{uploadImage()}

        binding.progressViewRipeness.apply {
            progressMax = 100f
            roundBorder = true
            setProgressWithAnimation(70f, 2000)
        }
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
                val service = ApiConfig.getApiService().uploadImage(imageMultipart,description,"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLVdtdGxBOUJUaGtLV25OMVQiLCJpYXQiOjE2NTQyMjI5Nzh9.NoMgRFGMMbVXyUnMyYZVmpc_INcl1b0ZXTSzauUzGMk")
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
                        Log.e("TAG", "onFailure: ${t.message}")
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