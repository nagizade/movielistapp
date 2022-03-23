package com.hasannagizade.movielistapp.presentation.tabs.toprated

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hasannagizade.movielistapp.data.model.MovieItem
import com.hasannagizade.movielistapp.data.model.Pagination
import com.hasannagizade.movielistapp.tools.BaseViewModel
import com.hasannagizade.movielistapp.usecases.MoviesUseCase

class TopRatedViewModel(
    private val getTopRatedUseCase: MoviesUseCase.GetTrending) : BaseViewModel() {

    private val _state = MutableLiveData<TopRatedState>()
    val state: LiveData<TopRatedState>
        get() = _state

    var pagination = Pagination()

    fun loadData() {
        launchAll {
            pagination.isLoading = true

            val movies = getTopRatedUseCase.getWith(
                MoviesUseCase.GetTrending.Params(pagination.nextPage)
            )

            val result = arrayListOf<MovieItem>()
            if (pagination.nextPage > 1) {

                (state.value as? TopRatedState.Show)?.data?.let { cachedList ->
                    result.addAll(cachedList)
                }
            }

            result.addAll(movies.results)

            pagination.hasNext = movies.page < movies.total_pages
            pagination.incrementOrSkip(pagination.hasNext)

            _state.postValue(TopRatedState.Show(result))
            pagination.isLoading = false
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