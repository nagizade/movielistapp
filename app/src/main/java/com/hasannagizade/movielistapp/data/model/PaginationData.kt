package com.hasannagizade.movielistapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PaginationData<T>(
    val results: List<T>,
    val page: Int,
    val total_pages: Int
)