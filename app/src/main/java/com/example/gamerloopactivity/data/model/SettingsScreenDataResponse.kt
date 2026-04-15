package com.example.gamerloopactivity.data.model

import com.google.gson.annotations.SerializedName

data class SettingsScreenDataResponse (
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("platform") val platform: String?,
    @SerializedName("publisher") val publisher: String?,
    @SerializedName("minimum_system_requirements") val minimumSystemRequirements: String?,
    @SerializedName("processor") val processor: String?,
    @SerializedName("memory") val memory: String?,
    @SerializedName("graphics") val graphics: String?,
    @SerializedName("storage") val storage: String?
)