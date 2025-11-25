package com.example.gamerloopactivity.data.model

import com.google.gson.annotations.SerializedName

data class ContentScreenDataResponse (
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("short_description") val shortDescription: String?,
    @SerializedName("thumbnail") val thumbnail: String?
)