package com.capstone.agrapanaapp.view.authentication.register

import com.google.gson.annotations.SerializedName

data class RequestBodyRegister(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String
)
