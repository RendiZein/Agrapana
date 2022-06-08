package com.capstone.agrapanaapp.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.agrapanaapp.model.UserModel
import com.capstone.agrapanaapp.model.UserPreference

class MainViewModel(private val pref: UserPreference ): ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }
    fun getEmail(): LiveData<String> {
        return pref.getEmail().asLiveData()
    }
}