package com.capstone.agrapanaapp.view.helper

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.agrapanaapp.model.UserPreference
import com.capstone.agrapanaapp.view.authentication.login.LoginViewModel
import com.capstone.agrapanaapp.view.result.ResultViewModel

class ViewModelFactory(private val pref: UserPreference,
                       private val context: Context,
                       private val dataStore: DataStore<Preferences>
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}