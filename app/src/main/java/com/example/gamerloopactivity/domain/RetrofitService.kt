package com.example.gamerloopactivity.domain

import com.example.gamerloopactivity.data.model.ContentScreenDataResponse
import com.example.gamerloopactivity.data.model.InfoScreenDataResponse
import com.example.gamerloopactivity.data.model.SettingsScreenDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitService {
    @GET("games")
    suspend fun getGames(): Response<List<ContentScreenDataResponse>>

    @GET("game")
    suspend fun getGameDetails(
        @Query("id") gameId: Int
    ): Response<InfoScreenDataResponse>

    @GET("game")
    suspend fun getGameSettings(
        @Query("id") gameId: Int
    ): Response<SettingsScreenDataResponse>
}
