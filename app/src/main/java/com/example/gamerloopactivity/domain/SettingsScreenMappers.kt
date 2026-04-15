package com.example.gamerloopactivity.domain

import android.os.Parcelable
import com.example.gamerloopactivity.data.model.InfoScreenDataResponse
import com.example.gamerloopactivity.data.model.SettingsScreenDataResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class SettingsMappers(
    val id: Int?,
    val title: String?,
    val platform: String?,
    val publisher: String?,
    val minimumSystemRequirements: String?,
    val processor: String?,
    val memory: String?,
    val graphics: String?,
    val storage: String?
): Parcelable
    fun SettingsScreenDataResponse.toSettingsMappers(): SettingsMappers {
        return SettingsMappers(
            id = this.id,
            title = this.title ?: "",
            platform = this.platform ?: "",
            publisher = this.publisher ?: "",
            minimumSystemRequirements = this.minimumSystemRequirements ?: "",
            processor = this.processor ?: "",
            memory = this.memory ?: "",
            graphics = this.graphics ?: "",
            storage = this.storage ?: "",
        )
    }