package com.capstone.agrapanaapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Fruit(
    var name: String,
    var description: String,
    var photo: Int
) : Parcelable
