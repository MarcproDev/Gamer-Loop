package com.example.gamerloopactivity.domain

import com.example.gamerloopactivity.data.model.ContentScreenDataResponse
import retrofit2.Response
import retrofit2.http.GET


interface RetrofitService {
    @GET("games")
    suspend fun getGames(): Response<List<ContentScreenDataResponse>>
  //suspend fun getGames(@Query("tag") query: String): Response<List<ContentScreenDataResponse>>
}