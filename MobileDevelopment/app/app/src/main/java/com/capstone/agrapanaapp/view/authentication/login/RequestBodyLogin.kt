package com.capstone.agrapanaapp.view.authentication.login

import com.google.gson.annotations.SerializedName

data class RequestBodyLogin(

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("password")
	val password: String


)
