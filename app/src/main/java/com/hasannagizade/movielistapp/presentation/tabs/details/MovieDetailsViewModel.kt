package com.hasannagizade.movielistapp.presentation.tabs.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hasannagizade.movielistapp.data.model.MovieItem
import com.hasannagizade.movielistapp.tools.BaseViewModel
import com.hasannagizade.movielistapp.usecases.MoviesUseCase

class MovieDetailsViewModel(
    private val addToWatchlist: MoviesUseCase.AddToWatchlist,
    private val removeFromWatchlist: MoviesUseCase.RemoveFromWatchlist,
    private val getMovie: MoviesUseCase.GetMovie
) : BaseViewModel() {

    private val _state = MutableLiveData<MovieDetailsState>()
    val state: LiveData<MovieDetailsState>
        get() = _state


    fun addToWatchlist(movieItem: MovieItem) {
        launchAll {
            addToWatchlist.execute(
                MoviesUseCase.AddToWatchlist.Params(movieItem)
            ) {
                onSuccess = {
                    _state.postValue(MovieDetailsState.SuccessAddWatchlist)
                }
            }
        }
    }

    fun removeFromWatchlist(movieId: Int) {
        launchAll {
            removeFromWatchlist.execute(
                MoviesUseCase.RemoveFromWatchlist.Params(movieId)
            ) {
                onSuccess = {
                    _state.postValue(MovieDetailsState.SuccessRemoveWatchlist)
                }
            }
        }
    }

    fun getMovieFromWatchlist(movieId: Int){
        launchAll{
            val movieItem = getMovie.getWith(MoviesUseCase.GetMovie.Params(movieId))
            _state.postValue(MovieDetailsState.LocalMovie(movieItem))
        }
    }

}


sealed class MovieDetailsState {
    object SuccessAddWatchlist : MovieDetailsState()
    object SuccessRemoveWatchlist : MovieDetailsState()
    class LocalMovie(val movieItem: MovieItem?) : MovieDetailsState()
}