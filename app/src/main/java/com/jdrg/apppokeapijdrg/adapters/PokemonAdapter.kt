package com.jdrg.apppokeapijdrg.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jdrg.apppokeapijdrg.databinding.ItemPokemonBinding
import com.jdrg.apppokeapijdrg.models.PokeResult

class  PokemonAdapter(private val onPokemonClick: (PokeResult) -> Unit) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {
    private var pokemonList = emptyList<PokeResult>()

    class PokemonViewHolder(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.binding.pokemonText.text = "#${position + 1} - ${pokemon.name}"
        holder.itemView.setOnClickListener { onPokemonClick(pokemon) }
    }

    override fun getItemCount() = pokemonList.size

    fun setPokemonList(pokemonList: List<PokeResult>) {
        this.pokemonList = pokemonList
        notifyDataSetChanged()
    }
}