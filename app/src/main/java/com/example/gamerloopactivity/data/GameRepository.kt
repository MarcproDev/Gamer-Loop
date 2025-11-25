package com.example.gamerloopactivity.data

import android.util.Log
import com.example.gamerloopactivity.domain.ContentInfo
import com.example.gamerloopactivity.domain.ContentMappers.toContentInfo
import com.example.gamerloopactivity.domain.InfoGameMappers.toInfoMappers
import com.example.gamerloopactivity.domain.InfoMappers
import com.example.gamerloopactivity.domain.RetrofitService
import javax.inject.Inject

private const val TAG = "GameRepository"

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

    suspend fun getGameDetails(gameId: Int): InfoMappers? {
        Log.d(TAG, "Llamando a la API para detalles del juego ID: $gameId")

        return try {
            val response = apiService.getGameDetails(gameId)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Log.d(TAG, "Detalles del juego ID: $gameId recibidos y mapeados correctamente.")
                    body.toInfoMappers()
                } else {
                    Log.e(
                        TAG,
                        "Error 200/204: La API de detalles devolvió un cuerpo nulo para el ID: $gameId"
                    )
                    null
                }
            } else {
                Log.e(
                    TAG,
                    "Error API ${response.code()}: No se pudo obtener el juego ID: $gameId. Mensaje: ${response.message()}"
                )
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Excepción de red/parsing al obtener el juego ID: $gameId", e)
            null
        }
    }
}