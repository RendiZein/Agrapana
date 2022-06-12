package com.capstone.agrapanaapp.data.remote.retrofit

import com.capstone.agrapanaapp.model.ResponseRegister
import com.capstone.agrapanaapp.view.authentication.register.RequestBodyRegister
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceRegister {
    @POST("users")
    fun register(
        @Body description: RequestBodyRegister,
    ): Call<ResponseRegister>
}