package com.capstone.agrapanaapp.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

	@field:SerializedName("data")
	val data: String? = null,

	@field:SerializedName("success")
	val success: Int? = null
)
