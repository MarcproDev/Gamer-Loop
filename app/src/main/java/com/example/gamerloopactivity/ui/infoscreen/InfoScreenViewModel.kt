package com.example.gamerloopactivity.ui.infoscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamerloopactivity.data.GameRepository
import com.example.gamerloopactivity.ui.infoscreen.ui.InfoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

private const val TAG = "InfoScreenViewModel"

sealed class NavigationEvent {
    object NavigateBack : NavigationEvent()
}

@HiltViewModel
class InfoScreenViewModel @Inject constructor(
    private val repository: GameRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<InfoUiState>(InfoUiState.Empty)
    val uiState: StateFlow<InfoUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<NavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun fetchGameDetails(gameId: Int) {
        if (gameId <= 0) {
            _uiState.value = InfoUiState.Error("ID de juego no válido: $gameId")
            return
        }
        viewModelScope.launch {
            _uiState.value = InfoUiState.Loading
            val details = repository.getGameDetails(gameId)
            if (details != null) {
                Log.d(TAG, "Detalles del juego ID: $gameId cargados con éxito.")
                _uiState.value = InfoUiState.Success(details)
            } else {
                val errorMessage =
                    "No se encontraron detalles para el juego ID: $gameId. (Error en la API o el ID no existe)"
                Log.e(TAG, errorMessage)
                _uiState.value = InfoUiState.Error(errorMessage)
            }
        }
    }

    fun onBackClicked() {
        viewModelScope.launch {
            _navigationEvent.send(NavigationEvent.NavigateBack)
        }
    }
}