package com.capstone.agrapanaapp.data.remote.retrofit

import com.capstone.agrapanaapp.model.ErrorResponse
import com.capstone.agrapanaapp.model.FileUploadResponse
import com.capstone.agrapanaapp.model.MachineLearningResponse
import com.capstone.agrapanaapp.model.ResponseLogin
import com.capstone.agrapanaapp.view.authentication.login.RequestBodyLogin
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

//    @FormUrlEncoded
//    @POST("register")
//    fun register(
//        @Field("name") name: String,
//        @Field("email") email: String,
//        @Field("password") password: String
//    ): Call<ResponseRegister>
//

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseLogin>

    @POST("login")
    fun signUp(
    @Body description: RequestBodyLogin,
    ): Call<ResponseLogin>

    @Multipart
    @POST("upload")
    fun uploadImage(
        @Part file: MultipartBody.Part
//        @Part("description") description: RequestBody,
//        @Header("Authorization") token : String
    ): Call<MachineLearningResponse>

}

