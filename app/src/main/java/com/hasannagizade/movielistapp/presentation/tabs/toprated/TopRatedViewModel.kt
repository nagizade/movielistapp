package com.hasannagizade.movielistapp.presentation.tabs.toprated

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hasannagizade.movielistapp.data.api.MovieRepository
import com.hasannagizade.movielistapp.data.model.MovieItem
import com.hasannagizade.movielistapp.data.model.Pagination
import com.hasannagizade.movielistapp.tools.BaseViewModel
import com.hasannagizade.movielistapp.usecases.MoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TopRatedViewModel(
    private val getTopRatedUseCase: MoviesUseCase.GetTrending,
    private val movieRepository: MovieRepository
) : BaseViewModel() {

    private val _state = MutableLiveData<TopRatedState>()
    val state: LiveData<TopRatedState>
        get() = _state

    var pagination = Pagination()

    fun loadData() {
        getTopRatedUseCase.launch(
            MoviesUseCase.GetTrending.Params(pagination.nextPage),
            loadingHandle = {
                pagination.isLoading = it
            }
        ) {

            onSuccess = {

                val result = arrayListOf<MovieItem>()
                if (pagination.nextPage > 1) {

                    (_state.value as? TopRatedState.Show)?.data?.let { cachedList ->
                        result.addAll(cachedList)
                    }
                }

                result.addAll(it.results)
                _state.postValue(TopRatedState.Show(result))

                pagination.hasNext = it.page < it.total_pages
                pagination.incrementOrSkip()
            }
            onError = {
            }
        }
    }

    fun reloadData() {
        pagination = Pagination()
        loadData()
    }
}

sealed class TopRatedState {
    class Show(val data: List<MovieItem>) : TopRatedState()
}