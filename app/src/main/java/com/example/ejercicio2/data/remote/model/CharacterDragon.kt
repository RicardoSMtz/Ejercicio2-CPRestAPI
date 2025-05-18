package com.example.ejercicio2.data.remote.model

import com.google.gson.annotations.SerializedName


data class CharacterDragon(
    @SerializedName ("items")
    val info: List<Character>
)
data class Character(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("affiliation")
    val affiliation: String
)
