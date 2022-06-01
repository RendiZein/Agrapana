package com.capstone.agrapanaapp.view.authentication.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.agrapanaapp.data.remote.retrofit.ApiConfig
import com.capstone.agrapanaapp.model.UserModel
import com.capstone.agrapanaapp.model.UserPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference) : ViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String> = _response

//    private val _userdata = MutableLiveData<LoginResult>()
//    val userdata: LiveData<LoginResult> = _userdata

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "LoginViewModel"
        private const val YES = "yes"
        private const val NO = "no"
        private const val UNAUTHORIZED = "unauthorized"
    }

//    fun login(email: String, password: String) {
//        _isLoading.value = true
//        _response.value = "none"
//        val client = ApiConfig.getApiService().login(email, password)
//        client.enqueue(object : Callback<ResponseLogin> {
//            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    _userdata.value = response.body()?.loginResult
//                    _response.value = YES
//                } else {
//                    Log.e(TAG, "onFailure: ${response.message()}")
//                    if (response.code() == 401) {
//                        _response.value = UNAUTHORIZED
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//                _response.value = NO
//            }
//        })
//    }

    fun loginData(user: UserModel) {
        viewModelScope.launch {
            pref.login(user)
        }
    }
}
