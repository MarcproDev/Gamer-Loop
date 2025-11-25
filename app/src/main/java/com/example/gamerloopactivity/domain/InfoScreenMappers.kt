package com.example.gamerloopactivity.domain
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import com.example.gamerloopactivity.data.model.InfoScreenDataResponse

@Parcelize
data class InfoMappers(
    val id: Int?,
    val title: String,
    val description: String,
    val gameUrl: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val releaseDate: String,
    val image: String
): Parcelable

object InfoGameMappers {
    fun InfoScreenDataResponse.toInfoMappers(): InfoMappers {
        return InfoMappers(
            id = this.id,
            title = this.title ?: "",
            description = this.description ?: "",
            gameUrl = this.gameUrl ?: "",
            genre = this.genre ?: "",
            platform = this.platform ?: "",
            publisher = this.publisher ?: "",
            releaseDate = this.releaseDate ?: "",
            image = this.image ?: "",
        )
    }
}