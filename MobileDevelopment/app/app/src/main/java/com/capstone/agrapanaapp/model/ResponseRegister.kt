package com.capstone.agrapanaapp.model

import com.google.gson.annotations.SerializedName

data class ResponseRegister(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("success")
	val success: Int
)

data class Data(

	@field:SerializedName("fieldCount")
	val fieldCount: Int,

	@field:SerializedName("serverStatus")
	val serverStatus: Int,

	@field:SerializedName("protocol41")
	val protocol41: Boolean,

	@field:SerializedName("changedRows")
	val changedRows: Int,

	@field:SerializedName("affectedRows")
	val affectedRows: Int,

	@field:SerializedName("warningCount")
	val warningCount: Int,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("insertId")
	val insertId: Int
)
