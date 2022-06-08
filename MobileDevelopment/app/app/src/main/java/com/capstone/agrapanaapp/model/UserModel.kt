package com.capstone.agrapanaapp.model

data class UserModel(
    val email: String,
    val name: String,
    val token: String,
    val isLogin: Boolean
)