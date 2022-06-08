package com.capstone.agrapanaapp.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow


class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[EMAIL_KEY] ?: "",
                preferences[USERNAME_KEY] ?:"",
                preferences[TOKEN_KEY] ?:"",
                preferences[STATE_KEY] ?: false
            )
        }
    }

    fun getEmail(): Flow<String> {
        return dataStore.data.map { it[EMAIL_KEY]?:""}
    }

    fun getToken() : Flow<String>{
        return dataStore.data.map { it[TOKEN_KEY] ?:"" }
    }

    suspend fun clearUser() {
        dataStore.edit {
            it.clear()
        }
    }

//    suspend fun login(user: UserModel) {
//        dataStore.edit { preferences ->
//            preferences[USERNAME_KEY] = user.name
//            preferences[TOKEN_KEY] = user.token
//            preferences[STATE_KEY] = user.isLogin
//        }
//    }

    suspend fun login(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = user.email
            preferences[USERNAME_KEY] = user.name
            preferences[TOKEN_KEY] = user.token
            preferences[STATE_KEY] = user.isLogin
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = ""
            preferences[USERNAME_KEY] = ""
            preferences[TOKEN_KEY] = ""
            preferences[STATE_KEY] = false
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val EMAIL_KEY = stringPreferencesKey("email")
        private val USERNAME_KEY = stringPreferencesKey("userName")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}