package br.com.andre.appandrenagem.vo


import com.google.gson.annotations.SerializedName

data class GenreX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)