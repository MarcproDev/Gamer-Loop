package com.example.gamerloopactivity.data.model

import com.google.gson.annotations.SerializedName

data class ContentScreenDataResponse (
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("short_description") val shortDescription: String?,
    @SerializedName("game_url") val gameUrl: String?,
    @SerializedName("platform") val platform: String?,
    @SerializedName("publisher") val publisher: String?,
    @SerializedName("thumbnail") val thumbnail: String?
)