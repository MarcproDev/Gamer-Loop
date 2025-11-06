package com.example.gamerloopactivity.domain

import com.example.gamerloopactivity.data.model.ContentScreenDataResponse

data class ContentInfo(
    val id: Any,
    val title: String,
    val shortDescription: String,
    val thumbnail: String,
    val gameUrl: String,
    val platform: String,
    val publisher: String
)

object ContentMappers {
    fun ContentScreenDataResponse.toContentInfo(): ContentInfo {
        return ContentInfo(
            id = this.id ?: "",
            title = this.title ?: "",
            shortDescription = this.shortDescription ?: "Descripción no disponible",
            thumbnail = this.thumbnail ?: "",
            gameUrl = this.gameUrl ?: "",
            platform = this.platform ?: "",
            publisher = this.publisher ?: ""
        )
    }
}