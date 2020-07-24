package br.com.andre.appandrenagem.vo

import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("genre_ids")
    val genreIds : List<Int>,
    @SerializedName("genres")
    val genres: List<GenreX>,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String

)