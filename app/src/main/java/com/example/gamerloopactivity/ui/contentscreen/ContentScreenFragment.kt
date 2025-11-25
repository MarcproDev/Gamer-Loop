package com.example.gamerloopactivity.ui.contentscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamerloopactivity.databinding.FragmentContentscreenBinding
import com.example.gamerloopactivity.ui.contentscreen.adapter.ContentScreenAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ContentScreenFragment : Fragment() {

    private val TAG = "ContentScreenFragment"
    private val viewModel: ContentScreenViewModel by viewModels()
    private var _binding: FragmentContentscreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var contentScreenAdapter: ContentScreenAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentscreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadAllContent()
        setupRecyclerView()
        setupSearchView()
        setupObservers()
    }

    private fun setupRecyclerView() {

        contentScreenAdapter = ContentScreenAdapter { gameId ->
            navigateToInfoScreen(gameId)
        }
        binding.recyclerViewGames.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contentScreenAdapter
        }
    }

    private fun navigateToInfoScreen(gameId: String) {
        val intGameId = gameId.toIntOrNull()

        if (intGameId == null) {
            Log.e(TAG, "Error: El gameId recibido ('$gameId') no es un número válido.")
            return
        }
        val gamesList = viewModel.contentInfo.value
        val selectedGame = gamesList.find { it.id == intGameId }

        if (selectedGame != null) {
            val finalGameId = selectedGame.id ?: 0
            val action = ContentScreenFragmentDirections.actionGlobalToInfoScreenFragment(
                gameId = finalGameId)
            findNavController().navigate(action)
        } else {
            Log.e(
                TAG,
                "Error de navegación: No se encontró el juego con ID $gameId en la lista actual."
            )
        }
    }

    private fun setupSearchView() {
        binding.searchViewList.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchGames(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.searchGames(it) }
                return true
            }
        })
    }


    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.contentInfo.collectLatest { games ->
                Log.d(TAG, "UI Actualizada. Mostrando ${games.size} juegos.")
                contentScreenAdapter.submitList(games)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collectLatest { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

