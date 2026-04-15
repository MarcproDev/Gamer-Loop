package com.example.gamerloopactivity.ui.settingsscreen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.gamerloopactivity.R
import com.example.gamerloopactivity.databinding.FragmentSettingsScreenBinding
import com.example.gamerloopactivity.domain.SettingsMappers
import com.example.gamerloopactivity.ui.settingsscreen.ui.SettingsScreenUi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SettingsScreenFragment : Fragment(R.layout.fragment_settings_screen) {

    private val args: SettingsScreenFragmentArgs by navArgs()

    private val viewModel: SettingsScreenViewModel by viewModels()

    private var _binding: FragmentSettingsScreenBinding? = null
    private val binding get() = _binding!!

    private val TAG ="SettingsScreenFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            _binding = FragmentSettingsScreenBinding.bind(view)
            Log.d(TAG, "CHECK 1: Binding y vista inflados correctamente.")
        } catch (e: Exception) {
            Log.e(
                TAG,
                "ERROR CRÍTICO: Fallo al enlazar la vista (Binding). Esto puede ser un error de ID en el layout.",
                e
            )
            return
        }
        val gameId = args.gameId
        Log.d(TAG, "CHECK 2: ID del juego recibido para carga: $gameId")

        if (gameId > 0) {
            viewModel.getGameSettings(gameId)
        } else {
            Log.e(TAG, "ERROR: ID de juego inválido o nulo en Safe Args.")
        }
        collectUiState()
        collectNavigationEvents()
    }

    private fun collectUiState() {
        viewModel.uiState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { uiState ->
                when (uiState) {
                    is SettingsScreenUi.Loading -> {
                        Log.d(TAG, "UI State: Cargando...")
                    }

                    is SettingsScreenUi.Success -> {
                        bindGameData(uiState.getGameSettings)
                        Log.d(TAG, "UI State: Éxito. Datos mostrados.")
                    }

                    is SettingsScreenUi.Error -> {
                        Log.e(TAG, "UI State: Error: ${uiState.message}")
                    }

                    is SettingsScreenUi.Empty -> {
                        Log.d(TAG, "UI State: Vacío (esperando acción inicial).")
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun collectNavigationEvents() {
        viewModel.navigationEvent
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { event ->
                when (event) {
                    else -> {
                        Log.w(TAG, "Navigation Event: Evento no manejado o inesperado: $event")
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun bindGameData(gameDetails: SettingsMappers) {
        binding.apply {
            tvGameTitle.text = gameDetails.title
            tvPlatform.text = gameDetails.platform
            tvPublisher.text = gameDetails.publisher
            tvMinimumSystem.text = gameDetails.minimumSystemRequirements
            tvProcessor.text = gameDetails.processor
            tvMemory.text = gameDetails.memory
            tvGraphics.text = gameDetails.graphics
            tvStorage.text = gameDetails.storage
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(TAG, "onDestroyView: Binding liberado.")
    }
}
