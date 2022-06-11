package com.capstone.agrapanaapp.data.remote.retrofit

import com.capstone.agrapanaapp.model.MachineLearningResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiServiceUpImage {
    @Multipart
    @POST("upload")
    fun uploadImage(
        @Part file: MultipartBody.Part
    ): Call<MachineLearningResponse>
}