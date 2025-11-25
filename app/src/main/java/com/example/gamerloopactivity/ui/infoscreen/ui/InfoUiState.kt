package com.example.gamerloopactivity.ui.infoscreen.ui

import com.example.gamerloopactivity.domain.InfoMappers


sealed class InfoUiState {
    object Loading : InfoUiState()
    data class Success(val gameDetails: InfoMappers) : InfoUiState()
    data class Error(val message: String) : InfoUiState()
    object Empty : InfoUiState()
}