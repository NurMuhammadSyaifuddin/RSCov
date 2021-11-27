package com.project.rscov.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hospital(
    val name: String,
    val region: String,
    val province: String,
    val address: String,
    val phone: String,
    @SerializedName("image_url")
    val imageUrl: String
) : Parcelable
