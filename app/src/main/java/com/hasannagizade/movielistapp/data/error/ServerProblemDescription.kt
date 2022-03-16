package com.hasannagizade.movielistapp.data.error

import kotlinx.serialization.Serializable

@Serializable
class ServerProblemDescription(val status: Int = 400, val detail: String = "")