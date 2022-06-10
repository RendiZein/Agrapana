package com.capstone.agrapanaapp.model

import com.google.gson.annotations.SerializedName

data class MachineLearningResponse(

	@field:SerializedName("prediction")
	val prediction: Prediction,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class Prediction(

	@field:SerializedName("confident")
	val confident: Double,

	@field:SerializedName("detail")
	val detail: String,

	@field:SerializedName("class")
	val jsonMemberClass: String
)
