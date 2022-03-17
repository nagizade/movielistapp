package com.hasannagizade.movielistapp.presentation.tabs.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hasannagizade.movielistapp.data.model.MovieItem
import com.hasannagizade.movielistapp.tools.BaseViewModel

class MovieDetailsViewModel() : BaseViewModel() {

    private val _state = MutableLiveData<MovieDetailsState>()
    val state: LiveData<MovieDetailsState>
        get() = _state

}

sealed class MovieDetailsState {
    class Show(val data: MovieItem) : MovieDetailsState()
}