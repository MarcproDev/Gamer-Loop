package com.example.gamerloopactivity.ui.infoscreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.gamerloopactivity.R
import com.example.gamerloopactivity.databinding.FragmentInfoScreenBinding
import com.example.gamerloopactivity.ui.infoscreen.ui.InfoUiState
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.findNavController
import com.example.gamerloopactivity.domain.InfoMappers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class InfoScreenFragment : Fragment(R.layout.fragment_info_screen) {
    private val args: InfoScreenFragmentArgs by navArgs()
    private val viewModel: InfoScreenViewModel by viewModels()
    private var _binding: FragmentInfoScreenBinding? = null
    private val binding get() = _binding!!
    private val TAG = "InfoScreenFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            _binding = FragmentInfoScreenBinding.bind(view)
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
            viewModel.fetchGameDetails(gameId)
        } else {
            Log.e(TAG, "ERROR: ID de juego inválido o nulo en Safe Args.")
            binding.infoProgressBar.visibility = View.GONE
        }
        collectUiState()
        collectNavigationEvents()
    }

    private fun collectUiState() {
        viewModel.uiState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { uiState ->
                when (uiState) {
                    is InfoUiState.Loading -> {
                        binding.infoProgressBar.visibility = View.VISIBLE
                        binding.scrollDescription.visibility = View.GONE
                        Log.d(TAG, "UI State: Cargando...")
                    }

                    is InfoUiState.Success -> {
                        binding.infoProgressBar.visibility = View.GONE
                        binding.scrollDescription.visibility = View.VISIBLE
                        bindGameData(uiState.gameDetails)
                        Log.d(TAG, "UI State: Éxito. Datos mostrados.")
                    }

                    is InfoUiState.Error -> {
                        binding.infoProgressBar.visibility = View.GONE
                        binding.scrollDescription.visibility = View.GONE
                        Log.e(TAG, "UI State: Error: ${uiState.message}")
                    }

                    is InfoUiState.Empty -> {
                        binding.infoProgressBar.visibility = View.GONE
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
                    is NavigationEvent.NavigateBack -> {
                        Log.d(
                            TAG,
                            "Navigation Event: NavigateBack. Volviendo a la pantalla anterior."
                        )
                        findNavController().navigateUp()
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun bindGameData(gameDetails: InfoMappers) {
        binding.apply {
            textViewInfoTitle.text = gameDetails.title
            textViewDescription.text = gameDetails.description
            textViewReleaseDate.text = gameDetails.releaseDate
            textViewPublisher.text = gameDetails.publisher
            textViewGenre.text = gameDetails.genre
            textViewPlatform.text = gameDetails.platform

            Picasso.get()
                .load(gameDetails.image)
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(android.R.drawable.stat_notify_error)
                .into(imageViewInfo)

            val profileUrl = gameDetails.gameUrl
            textViewGameUrl.text = profileUrl

            if (profileUrl.isNotBlank()) {
                textViewGameUrl.setOnClickListener {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, profileUrl.toUri())
                        startActivity(intent)
                        Log.d(TAG, "Enlace abierto: $profileUrl")
                    } catch (e: Exception) {
                        Log.e(TAG, "ERROR: Fallo al abrir la URL: $profileUrl", e)
                    }
                }
            } else {
                textViewGameUrl.setOnClickListener(null) // Quitar el listener si no hay URL válida
                Log.w(TAG, "ADVERTENCIA: URL de juego vacía. Enlace inactivo.")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(TAG, "onDestroyView: Binding liberado.")
    }
}
