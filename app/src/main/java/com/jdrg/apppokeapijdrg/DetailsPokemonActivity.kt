package com.jdrg.apppokeapijdrg

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.jdrg.apppokeapijdrg.api.ApiClient
import com.jdrg.apppokeapijdrg.api.ApiService
import com.jdrg.apppokeapijdrg.databinding.ActivityDetailsPokemonBinding
import com.jdrg.apppokeapijdrg.models.Ability
import com.jdrg.apppokeapijdrg.models.Pokemon
import com.jdrg.apppokeapijdrg.models.Type
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsPokemonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsPokemonBinding
    private lateinit var apiService: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = ApiClient.getRetrofitInstance().create(ApiService::class.java)

        val pokemonId = intent.getIntExtra("POKEMON_ID", 0)
        getPokemonDetails(pokemonId)
    }

    private fun getPokemonDetails(id: Int) {
        apiService.getPokemonInfo(id).enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                runOnUiThread {
                    val pokemon = response.body()
                    if (pokemon != null) {
                        binding.textViewName.text = pokemon.name
                        binding.textViewBaseExperience.text =
                            "Experiencia base: ${pokemon.baseExperience}"
                        binding.textViewHeight.text = "Altura: ${pokemon.height}"
                        binding.textViewWeight.text = "Peso: ${pokemon.weight}"
                        Glide.with(this@DetailsPokemonActivity).load(pokemon.sprites.frontDefault)
                            .into(binding.imageViewPokemon)
                        // Continuar con la siguiente llamada a la API
                        getPokemonSpecies(id)
                    }
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                runOnUiThread {
                    Toast.makeText(
                        this@DetailsPokemonActivity,
                        "Error al cargar los detalles del Pokémon.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun getPokemonSpecies(id: Int) {
        apiService.getPokemonSpecies(id).enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                runOnUiThread {
                    val pokemonSpecies = response.body()
                    if (pokemonSpecies != null) {
                        binding.textViewSpecies.text = "Especies: ${pokemonSpecies.name}"
                        // Continuar con la siguiente llamada a la API
                        getPokemonAbilities(id)
                    }
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                runOnUiThread {
                    Toast.makeText(
                        this@DetailsPokemonActivity,
                        "Error al cargar las especies del Pokémon.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun getPokemonAbilities(id: Int) {
        apiService.getPokemonAbility(id).enqueue(object : Callback<Ability> {
            override fun onResponse(call: Call<Ability>, response: Response<Ability>) {
                runOnUiThread {
                    val ability = response.body()
                    if (ability != null && ability.ability != null) {
                        binding.textViewAbilities.text = "Habilidades: ${ability.ability.name}"
                        // Continuar con la siguiente llamada a la API
                        getPokemonTypes(id)
                    }
                }
            }

            override fun onFailure(call: Call<Ability>, t: Throwable) {
                runOnUiThread {
                    Toast.makeText(
                        this@DetailsPokemonActivity,
                        "Error al cargar las habilidades del Pokémon.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun getPokemonTypes(id: Int) {
        apiService.getPokemonType(id).enqueue(object : Callback<Type> {
            override fun onResponse(call: Call<Type>, response: Response<Type>) {
                runOnUiThread {
                    val type = response.body()
                    if (type != null && type.type != null) {
                        binding.textViewTypes.text = "Tipos: ${type.type.name}"
                    }
                }
            }

            override fun onFailure(call: Call<Type>, t: Throwable) {
                runOnUiThread {
                    Toast.makeText(
                        this@DetailsPokemonActivity,
                        "Error al cargar los tipos del Pokémon.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}