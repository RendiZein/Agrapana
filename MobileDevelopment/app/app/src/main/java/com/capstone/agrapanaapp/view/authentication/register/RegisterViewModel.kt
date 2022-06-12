package com.capstone.agrapanaapp.view.authentication.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.agrapanaapp.data.remote.retrofit.ApiConfig
import com.capstone.agrapanaapp.data.remote.retrofit.ApiConfigRegister
import com.capstone.agrapanaapp.model.ResponseRegister
import com.capstone.agrapanaapp.view.authentication.login.RequestBodyLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel: ViewModel() {

    private val _success = MutableLiveData<String>()
    val success: LiveData<String> = _success

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "SignupViewModel"
        private const val YES = "yes"
        private const val NO = "no"
        private const val TAKEN = "taken"
    }

    fun register(name : String,email : String,password : String) {
        _isLoading.value = true
        _success.value = "none"
        val myReq = RequestBodyRegister(password, name, email)
        val client = ApiConfigRegister.getApiService().register(myReq)
        client.enqueue(object : Callback<ResponseRegister>{
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _success.value = YES
                } else {
                    Log.e(TAG, "onFailure1: ${response.message()}")
                    if (response.code() == 400){_success.value = TAKEN}
                }
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _success.value = NO
            }

        })
//        client.enqueue(object : Callback<ResponseRegister> {
//            override fun onResponse(call: Call<ResponseRegister>, response: Response<ResponseRegister>) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    _success.value = YES
//                } else {
//                    Log.e(TAG, "onFailure: ${response.message()}")
//                    if (response.code() == 400){_success.value = TAKEN}
//                }
//            }
//            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//                _success.value = NO
//            }
//        })
    }
}
