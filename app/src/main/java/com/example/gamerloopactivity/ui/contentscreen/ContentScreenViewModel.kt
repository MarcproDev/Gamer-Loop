package com.example.gamerloopactivity.ui.contentscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamerloopactivity.data.GameRepository
import com.example.gamerloopactivity.domain.ContentInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ContentScreenViewModel @Inject constructor(
    private val repository: GameRepository
) : ViewModel() {

    private val TAG = "ContentScreenVM"
    private var _allContentInfo: List<ContentInfo> = emptyList()
    private val _contentInfo = MutableStateFlow<List<ContentInfo>>(emptyList())
    val contentInfo: StateFlow<List<ContentInfo>> = _contentInfo.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    fun loadAllContent() {
        if (_allContentInfo.isNotEmpty() || _isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            Log.d(TAG, "Iniciando carga de TODOS los juegos (API).")

            try {
                val response = repository.fetchAllGames()

                if (response.isNotEmpty()) {
                    _allContentInfo = response
                    _contentInfo.value = response
                    Log.i(TAG, "Carga API exitosa. Total juegos: ${response.size}")
                } else {
                    Log.w(TAG, "Carga API exitosa, pero lista vacía.")
                }

            } catch (e: Exception) {
                Log.e(TAG, "Excepción al cargar el contenido: ${e.message}", e)
                _contentInfo.value = emptyList()

            } finally {
                _isLoading.value = false
                Log.d(TAG, "Finalizada la operación de carga.")
            }
        }
    }

    fun searchGames(query: String) {
        viewModelScope.launch {
            val trimmedQuery = query.trim()
            Log.d(TAG, "Iniciando filtrado local para: $trimmedQuery")
            if (trimmedQuery.isEmpty()) {
                _contentInfo.value = _allContentInfo
            } else {
                val lowerCaseQuery = trimmedQuery.lowercase(Locale.getDefault())
                val filteredGames = _allContentInfo.filter { game ->
                    game.title.lowercase(Locale.getDefault()).contains(lowerCaseQuery)
                }
                if (_allContentInfo.isEmpty()) {
                    Log.w(
                        TAG,
                        "La lista maestra de juegos (_allContentInfo) está vacía. No se pudo filtrar."
                    )

                } else if (filteredGames.isEmpty()) {
                    Log.d(TAG, "Filtro completado: 0 resultados encontrados para: '$trimmedQuery'")
                }
                _contentInfo.value = filteredGames
            }
        }
    }
}