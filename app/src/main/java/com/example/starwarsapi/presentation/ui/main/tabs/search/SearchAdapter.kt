package com.example.starwarsapi.presentation.ui.main.tabs.search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapi.R
import com.example.starwarsapi.databinding.ItemFoundCharacterBinding

//todo this adapter almost like favorite adapter... i think it is stupid
class SearchAdapter(
    private val listener: Listener
) : RecyclerView.Adapter<SearchAdapter.Holder>(), View.OnClickListener {

    private var searchItems: List<CharacterItemUiState> = emptyList()

    override fun onClick(v: View) {
        val character = v.tag as  CharacterItemUiState

        when(v.id) {
            R.id.savedImageView -> {

                if(character.isBookmarked) {
                    Log.d("AAA", "For ${character.name} ${character.id} bookmarked ${character.isBookmarked} start deleting")
                    listener.deleteCharacter(character.id)
                } else {
                    Log.d("AAA", "For ${character.name} ${character.id} bookmarked ${character.isBookmarked} start saving")
                    listener.saveCharacter(character.id)
                    }
                }
            else -> {
                listener.openCharacter(character.id)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoundCharacterBinding.inflate(inflater, parent, false)

        binding.savedImageView.setOnClickListener(this)
        binding.root.setOnClickListener(this)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val character = searchItems[position]
        val binding = holder.binding

        holder.itemView.tag = character
        binding.savedImageView.tag = character

        binding.nameTextView.text = character.name


        if(character.isInProgress) {
            binding.savedImageView.visibility = View.INVISIBLE
            binding.saveProgressBar.visibility = View.VISIBLE
        } else {
            binding.savedImageView.visibility = View.VISIBLE
            binding.saveProgressBar.visibility = View.INVISIBLE
        }

        if(character.isBookmarked) {
            binding.savedImageView.setImageResource(R.drawable.ic_bookmark_saved)
        } else {
            binding.savedImageView.setImageResource(R.drawable.ic_bookmark_no_saved)
        }






    }

    override fun getItemCount() = searchItems.size



    fun renderSettings(items: List<CharacterItemUiState>) {
        searchItems = items
        notifyDataSetChanged()
    }

    class Holder(val binding: ItemFoundCharacterBinding) : RecyclerView.ViewHolder(binding.root)

    interface Listener {
        fun saveCharacter(id: Long)
        fun deleteCharacter(id: Long)
        fun openCharacter(id: Long)
    }



}