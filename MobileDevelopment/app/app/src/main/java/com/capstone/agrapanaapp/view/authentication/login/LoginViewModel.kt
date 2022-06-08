package com.capstone.agrapanaapp.view.authentication.login

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.agrapanaapp.R
import com.capstone.agrapanaapp.data.remote.retrofit.ApiConfig
import com.capstone.agrapanaapp.model.ErrorResponse
import com.capstone.agrapanaapp.model.ResponseLogin
import com.capstone.agrapanaapp.model.UserModel
import com.capstone.agrapanaapp.model.UserPreference
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.POST


class LoginViewModel(private val pref: UserPreference) : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String> = _response

    private val _userdata = MutableLiveData<ResponseLogin>()
    val userdata: LiveData<ResponseLogin> = _userdata

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "LoginViewModel"
        private const val YES = "yes"
        private const val NO = "no"
        private const val UNAUTHORIZED = "unauthorized"
    }

    fun login(email: String, password: String) {
        _isLoading.value = true
        _response.value = "none"
        val myReq = RequestBodyLogin(email, password)

        val client3 = ApiConfig.getApiService().signUp(myReq)
        client3.enqueue(object: Callback<ResponseLogin>{
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponseErr2: ${response.body()}")
                    _userdata.value = response.body()
                    _response.value = YES
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    if (response.code() == 401) {
                        _response.value = UNAUTHORIZED
                    }
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _response.value = NO
            }
        })

    }

    fun loginData(user: UserModel) {
        viewModelScope.launch {
            pref.login(user)
        }
    }

}
