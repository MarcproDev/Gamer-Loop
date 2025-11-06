package com.example.gamerloopactivity.data

import com.example.gamerloopactivity.domain.ContentInfo
import com.example.gamerloopactivity.domain.ContentMappers.toContentInfo
import com.example.gamerloopactivity.domain.RetrofitService
import javax.inject.Inject

class GameRepository @Inject constructor(
    private val apiService: RetrofitService
) {
    suspend fun fetchAllGames(): List<ContentInfo> {
        return try {
            val response = apiService.getGames()
            if (response.isSuccessful) {
                response.body()?.map { it.toContentInfo() } ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}