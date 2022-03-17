package com.hasannagizade.movielistapp.data.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class MovieItem(
    val adult: Boolean? = null,
    val backdrop_path: String? = null,
    val genre_ids: List<Int>? = null,
    val id: Int? = null,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val poster_path: String,
    val release_date: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null
) : Parcelable