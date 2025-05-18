package com.example.ejercicio2.data.remote.model
import com.google.gson.annotations.SerializedName

data class DetailDragon(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("ki")
    val ki: String?,
    @SerializedName("maxKi")
    val maxKi: String?,
    @SerializedName("race")
    val race: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("affiliation")
    val affiliation: String?,
    @SerializedName("transformations")
    val transformations: List<Transformation>?
)

data class Transformation(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("ki")
    val ki: String?
)
