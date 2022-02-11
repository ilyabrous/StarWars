package com.example.starwarsapi.presentation.ui.main.tabs.search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwarsapi.R
import com.example.starwarsapi.databinding.FragmentSearchBinding
import com.example.starwarsapi.domain.models.CharacterNameParam
import com.example.starwarsapi.presentation.ui.main.character.CharacterFragment
import com.example.starwarsapi.presentation.utils.findTopNavController
import com.example.starwarsapi.presentation.utils.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding : FragmentSearchBinding

    private val viewModel: SearchFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("AAA", "Search fragment - start")
        binding = FragmentSearchBinding.bind(view)

        binding.searchButton.setOnClickListener {
            val name = binding.characterEditText.text.toString()
            val characterNameParam = CharacterNameParam(name = name)
            viewModel.findCharacters(characterNameParam)
        }
        binding.characterEditText.addTextChangedListener { editable ->
            if(editable.toString().isNotEmpty()) {
                val characterNameParam = CharacterNameParam(name = editable.toString())
                viewModel.findCharacters(characterNameParam)
            }
        }

        val adapter = setupList()


        viewModel.openCharacter.observeEvent(viewLifecycleOwner) {
            findTopNavController().navigate(
                R.id.action_tabsFragment_to_characterFragment,
                bundleOf(CharacterFragment.ARG_CHARACTER_ID to it)
            )
        }

        viewModel.uiStateLiveData.observe(viewLifecycleOwner) { state ->
            adapter.renderSettings(state.characterItems)
            binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.INVISIBLE
            binding.characterRecyclerView.visibility = if (!state.isLoading) View.VISIBLE else View.INVISIBLE
        }

        viewModel.showErrorEvent.observeEvent(viewLifecycleOwner) {
            Toast.makeText(context, getString(it), Toast.LENGTH_SHORT).show()
        }



    }


    private fun setupList(): SearchAdapter {
        binding.characterRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = SearchAdapter(viewModel)
        binding.characterRecyclerView.adapter = adapter
        return adapter
    }
}