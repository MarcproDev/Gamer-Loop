package com.example.gamerloopactivity.data.model

import com.google.gson.annotations.SerializedName

data class InfoScreenDataResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("game_url") val gameUrl: String?,
    @SerializedName("genre") val genre: String?,
    @SerializedName("platform") val platform: String?,
    @SerializedName("publisher") val publisher: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("thumbnail") val image: String?
)