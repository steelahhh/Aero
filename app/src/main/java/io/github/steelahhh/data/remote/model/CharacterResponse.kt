package io.github.steelahhh.data.remote.model

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    val name: String,
    val height: String, // height in cm
    @SerializedName("birth_year")
    val birthYear: String,
    val gender: String,
    val homeworld: String, // home world link
    val species: List<String>, // species of this character
    val url: String, // detail url
    val films: List<String> // movie links
)
