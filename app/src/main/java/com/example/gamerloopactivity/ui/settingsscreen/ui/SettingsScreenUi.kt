package com.example.gamerloopactivity.ui.settingsscreen.ui

import com.example.gamerloopactivity.domain.SettingsMappers

sealed class SettingsScreenUi {
    object Loading : SettingsScreenUi()
    data class Success(val getGameSettings: SettingsMappers) : SettingsScreenUi()
    data class Error(val message: String) : SettingsScreenUi()
    object Empty : SettingsScreenUi()
}