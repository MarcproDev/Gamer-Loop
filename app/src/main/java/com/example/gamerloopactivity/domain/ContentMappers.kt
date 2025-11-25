package com.example.gamerloopactivity.domain

import com.example.gamerloopactivity.data.model.ContentScreenDataResponse

data class ContentInfo(
    val id: Int?,
    val title: String,
    val shortDescription: String,
    val thumbnail: String
)

object ContentMappers {
    fun ContentScreenDataResponse.toContentInfo(): ContentInfo {
        return ContentInfo(
            id = this.id,
            title = this.title ?: "",
            shortDescription = this.shortDescription ?: "",
            thumbnail = this.thumbnail ?: "",
        )
    }
}