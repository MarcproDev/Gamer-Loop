package com.example.gamerloopactivity.ui.settingsscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamerloopactivity.data.GameRepository
import com.example.gamerloopactivity.ui.settingsscreen.ui.SettingsScreenUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "SettingsScreenViewModel"

sealed class NavigationEvent {
    object NavigateBack : NavigationEvent()
}

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(private val repository: GameRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow<SettingsScreenUi>(SettingsScreenUi.Empty)
    val uiState: StateFlow<SettingsScreenUi> = _uiState.asStateFlow()

    private val _navigationEvent =
        Channel<NavigationEvent>(Channel.BUFFERED)
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun getGameSettings(gameId: Int) {
        if (gameId <= 0) {
            _uiState.value = SettingsScreenUi.Error("ID de juego no válido: $gameId")
            return
        }
        viewModelScope.launch {
            _uiState.value = SettingsScreenUi.Loading
            val details = repository.getGameDetails(gameId)
            if (details != null) {
                val settings = repository.getGameSettings(gameId)
                if (settings != null) {
                    Log.d(TAG, "Detalles del juego ID: $gameId y ajustes cargados con éxito.")
                    _uiState.value = SettingsScreenUi.Success(settings)
                } else {
                    val errorMessage =
                        "No se encontraron detalles para el juego ID: $gameId. (Error en la API o el ID no existe)"
                    Log.e(TAG, errorMessage)
                    _uiState.value = SettingsScreenUi.Error(errorMessage)
                }
            }
        }
    }

    fun onBackClicked() {
        viewModelScope.launch {
            _navigationEvent.send(NavigationEvent.NavigateBack)
        }
    }
}