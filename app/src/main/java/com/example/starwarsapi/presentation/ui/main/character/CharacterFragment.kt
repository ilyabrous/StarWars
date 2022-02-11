package com.example.starwarsapi.presentation.ui.main.character

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.starwarsapi.R
import com.example.starwarsapi.databinding.FragmentCharacterBinding
import com.example.starwarsapi.domain.models.Character
import com.example.starwarsapi.domain.models.CharacterId
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment : Fragment(R.layout.fragment_character) {

    private lateinit var binding: FragmentCharacterBinding
    private val viewModel: CharacterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterBinding.bind(view)
        Log.d("AAA", "Character fragment - start")

        val adapter = setupList()

        viewModel.characterUiStateLiveData.observe(viewLifecycleOwner) { state ->
            Log.d("AAA" , "$state")
            binding.progressBar.visibility = if(state.isLoading) View.VISIBLE else View.INVISIBLE
            if(state.character != null) {
                adapter.itemList =  parseCharacterToList(state.character)
            }
        }

        val id = requireArguments().getLong(ARG_CHARACTER_ID, 1)
        viewModel.getCharacterDetail(characterId = CharacterId(id = id))

    }


    private fun setupList(): CharacterAdapter {
        binding.characterRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = CharacterAdapter()
        binding.characterRecyclerView.adapter = adapter
        return adapter
    }

    private fun parseCharacterToList(character: Character): List<CharacterItemAdapter> {
        val list = mutableListOf<CharacterItemAdapter>()

        list.add(
            CharacterItemAdapter(
            item = "Name",
            description = character.name
        )
        )
        list.add(
            CharacterItemAdapter(
                item = "Birth Year",
                description = character.birthYear
            )
        )
        list.add(
            CharacterItemAdapter(
                item = "Gender",
                description = character.gender
            )
        )
        list.add(
            CharacterItemAdapter(
                item = "Eye Color",
                description = character.eyeColor
            )
        )
        list.add(
            CharacterItemAdapter(
                item = "Mass",
                description = character.mass
            )
        )
        list.add(
            CharacterItemAdapter(
                item = "Height",
                description = character.height
            )
        )
        list.add(
            CharacterItemAdapter(
                item = "Skin's color",
                description = character.skinColor
            )
        )
        list.add(
            CharacterItemAdapter(
                item = "Created",
                description = character.created
            )
        )
        list.add(
            CharacterItemAdapter(
                item = "Edited",
                description = character.edited
            )
        )
        list.add(
            CharacterItemAdapter(
                item = "Films",
                description = character.films?.map{it.title}?.joinToString(separator = ", ") ?: "Empty"
            )
        )
        list.add(
            CharacterItemAdapter(
                item = "Starships",
                description = character.starships?.map{it.name}?.joinToString(separator = ", ") ?: "Empty"
            )
        )
        list.add(
            CharacterItemAdapter(
                item = "Vehicles",
                description = character.vehicles?.map{it.name}?.joinToString(separator = ", ") ?: "Empty"
            )
        )
        list.add(
            CharacterItemAdapter(
                item = "Species",
                description = character.species?.map{it.name}?.joinToString(separator = ", ") ?: "Empty"
            )
        )



        return list
    }



    companion object {
        const val ARG_CHARACTER_ID = "arg_character_id"
    }
}