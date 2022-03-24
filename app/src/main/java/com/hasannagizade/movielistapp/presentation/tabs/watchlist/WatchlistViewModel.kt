package com.hasannagizade.movielistapp.presentation.tabs.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hasannagizade.movielistapp.data.model.MovieItem
import com.hasannagizade.movielistapp.tools.BaseViewModel
import com.hasannagizade.movielistapp.usecases.MoviesUseCase

class WatchlistViewModel(
    private val getWatchlist: MoviesUseCase.GetWatchlist,
) : BaseViewModel() {

    private val _state = MutableLiveData<WatchlistState>()
    val state: LiveData<WatchlistState>
        get() = _state

    private  val _loader = MutableLiveData<Boolean>()
    val loader: LiveData<Boolean>
        get() = _loader


    fun loadData() {
        launchAll {
            _loader.postValue(true)
            val movies = getWatchlist.getWith(Unit)

            _state.postValue(WatchlistState.Show(movies))
            _loader.postValue(false)
        }
    }

    fun reloadData() {
        loadData()
    }
}

sealed class WatchlistState {
    class Show(val data: List<MovieItem>) : WatchlistState()
}