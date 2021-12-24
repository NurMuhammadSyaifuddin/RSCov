package com.project.rscov.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hospital(
    var name: String? = null,
    var region: String? = null,
    var province: String? = null,
    var address: String? = null,
    var phone: String? = null,
    @SerializedName("image_url")
    var imageUrl: String? = null
) : Parcelable
