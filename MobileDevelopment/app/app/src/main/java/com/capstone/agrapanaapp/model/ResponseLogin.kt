package com.capstone.agrapanaapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseLogin(

	@field:SerializedName("success")
	val success: Int,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("token")
	val token: String
) : Parcelable
