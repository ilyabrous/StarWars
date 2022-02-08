package com.example.starwarsapi.presentation.ui.main.tabs.favorite

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwarsapi.R
import com.example.starwarsapi.databinding.FragmentFavoriteBinding
import com.example.starwarsapi.presentation.ui.main.character.CharacterFragment
import com.example.starwarsapi.presentation.utils.findTopNavController
import com.example.starwarsapi.presentation.utils.observeEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var binding: FragmentFavoriteBinding

    private val viewModel : FavoriteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)

        val adapter = setupList()

        viewModel.favoriteItemsUiStateLiveDate.observe(viewLifecycleOwner) {
            adapter.renderSettings(it.listItem)
        }
        viewModel.openCharacter.observeEvent(viewLifecycleOwner) {
            findTopNavController().navigate(
                R.id.action_tabsFragment_to_characterFragment,
                bundleOf(CharacterFragment.ARG_CHARACTER_ID to it)
            )
        }
        viewModel.showEvent.observeEvent(viewLifecycleOwner) {
            Toast.makeText(context, getString(it), Toast.LENGTH_SHORT).show()
        }
        viewModel.showUndoSnackbar.observeEvent(viewLifecycleOwner) {
            Snackbar.make(view, "Character is Deleted", Snackbar.LENGTH_SHORT)
                .setAction("Undo") { _ ->
                    viewModel.saveCharacter(it)
                }
                .show()
        }
    }

    private fun setupList(): FavoriteAdapter {
        binding.characterRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = FavoriteAdapter(viewModel)
        binding.characterRecyclerView.adapter = adapter
        return adapter
    }

}