package com.capstone.agrapanaapp.view.result

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.agrapanaapp.R
import com.capstone.agrapanaapp.data.remote.retrofit.ApiConfigUpImage
import com.capstone.agrapanaapp.databinding.ActivityResultBinding
import com.capstone.agrapanaapp.model.MachineLearningResponse
import com.capstone.agrapanaapp.model.UserPreference
import com.capstone.agrapanaapp.view.helper.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.math.BigDecimal
import java.math.RoundingMode


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
            finish()
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


    }

    private fun setupView(confident: Double, jsonMemberClass: String, detail: String) {

        val decimal = BigDecimal(confident).setScale(2, RoundingMode.HALF_EVEN)
        val percentage = decimal.toFloat()*100
        binding.progressViewRipeness.visibility = View.VISIBLE
        binding.progressViewRipeness.apply {
            progressMax = 100f
            roundBorder = true
            setProgressWithAnimation(percentage, 2000)
        }


        if (jsonMemberClass == "ripe"){
            binding.tvRipResult.visibility = View.VISIBLE
            binding.tvRipResult.text = "Matang"
        } else if (jsonMemberClass == "green"){
            binding.tvRipResult.visibility = View.VISIBLE
            binding.tvRipResult.text = "Belum matang"
        } else {
            binding.tvRipResult.visibility = View.VISIBLE
            binding.tvRipResult.text = "Terlalu matang"
        }

        binding.tvNumPercent.visibility = View.VISIBLE
        binding.tvNumPercent.text = "${percentage.toInt()}"

        binding.tvMessage.visibility = View.VISIBLE
        binding.tvMessage.text = detail

        binding.tvPercent.visibility = View.VISIBLE
    }

    private fun uploadImage() {
        binding.progressBar.visibility =  View.VISIBLE
        if (getFile != null) {

            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
                file.name,
                requestImageFile
            )

                resultViewModel.getUser().observe(this) { user ->
                val service = ApiConfigUpImage.getApiServiceUpImage().uploadImage(imageMultipart)
                service.enqueue(object : Callback<MachineLearningResponse> {
                    override fun onResponse(
                        call: Call<MachineLearningResponse>,
                        response: Response<MachineLearningResponse>
                    ) {
                        if (response.isSuccessful) {
                            binding.progressBar.visibility =  View.GONE
                            val responseBody = response.body()
                            if (responseBody != null) {
                                Log.d("TAG", "onResponse1: ${response.body()}")
                                Toast.makeText(
                                    this@ResultActivity,
                                    responseBody.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                                setupView(responseBody.prediction.confident, responseBody.prediction.jsonMemberClass, responseBody.prediction.detail)
                            }
                        } else {
                            binding.progressBar.visibility =  View.GONE
                            Log.d("TAG", "onResponse2: ${response.body()}")
                            Toast.makeText(this@ResultActivity, response.message(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<MachineLearningResponse>, t: Throwable) {
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