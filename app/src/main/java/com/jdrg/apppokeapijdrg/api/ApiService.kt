package com.jdrg.apppokeapijdrg.api

import com.jdrg.apppokeapijdrg.models.Ability
import com.jdrg.apppokeapijdrg.models.Pokemon
import com.jdrg.apppokeapijdrg.models.PokemonResponse
import com.jdrg.apppokeapijdrg.models.Type
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon/{id}")
    fun getPokemonInfo(@Path("id") id: Int): Call<Pokemon>

    @GET("pokemon")
    fun getPokemonList(@Query("limit") limit: Int, @Query("offset") offset: Int): Call<PokemonResponse>

    @GET("pokemon-species/{id}")
    fun getPokemonSpecies(@Path("id") id: Int): Call<Pokemon>

    @GET("ability/{id}")
    fun getPokemonAbility(@Path("id") id: Int): Call<Ability>

    @GET("type/{id}")
    fun getPokemonType(@Path("id") id: Int): Call<Type>
}