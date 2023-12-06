package com.jdrg.apppokeapijdrg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jdrg.apppokeapijdrg.adapters.PokemonAdapter
import com.jdrg.apppokeapijdrg.api.ApiClient
import com.jdrg.apppokeapijdrg.api.ApiService
import com.jdrg.apppokeapijdrg.databinding.ActivityMainBinding
import com.jdrg.apppokeapijdrg.models.PokemonResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var apiService: ApiService
    private lateinit var pokemonAdapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = ApiClient.getRetrofitInstance().create(ApiService::class.java)
        pokemonAdapter = PokemonAdapter { pokemon ->
            val intent = Intent(this, DetailsPokemonActivity::class.java)
            intent.putExtra("POKEMON_ID", pokemon.id)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = pokemonAdapter

        getPokemonList()
    }

    private fun getPokemonList() {
        apiService.getPokemonList(1000, 0).enqueue(object : Callback<PokemonResponse> {
            override fun onResponse(call: Call<PokemonResponse>, response: Response<PokemonResponse>) {
                val pokemonList = response.body()?.results
                if (pokemonList != null) {
                    pokemonAdapter.setPokemonList(pokemonList)
                }
            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                // Manejar el error
            }
        })
    }
}