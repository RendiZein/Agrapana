package com.capstone.agrapanaapp.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.agrapanaapp.model.UserPreference
import kotlinx.coroutines.launch

class ProfileViewModel(private val pref: UserPreference):ViewModel() {
    fun logoutUser() {
        viewModelScope.launch {
            pref.clearUser()
        }
    }
    fun getEmail(): LiveData<String> {
        return pref.getEmail().asLiveData()
    }
}