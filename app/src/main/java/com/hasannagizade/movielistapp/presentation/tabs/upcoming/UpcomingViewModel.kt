package com.hasannagizade.movielistapp.presentation.tabs.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hasannagizade.movielistapp.data.model.MovieItem
import com.hasannagizade.movielistapp.data.model.Pagination
import com.hasannagizade.movielistapp.tools.BaseViewModel
import com.hasannagizade.movielistapp.usecases.MoviesUseCase

class UpcomingViewModel(
    private val getUpcoming: MoviesUseCase.GetUpcoming,
) : BaseViewModel() {

    private val _state = MutableLiveData<UpcomingState>()
    val state: LiveData<UpcomingState>
        get() = _state

    var pagination = Pagination()

    fun loadData() {
        launchAll {
            pagination.isLoading = true

            val movies = getUpcoming.getWith(
                MoviesUseCase.GetUpcoming.Params(pagination.nextPage)
            )

            val result = arrayListOf<MovieItem>()
            if (pagination.nextPage > 1) {

                (state.value as? UpcomingState.Show)?.data?.let { cachedList ->
                    result.addAll(cachedList)
                }
            }

            result.addAll(movies.results)

            pagination.hasNext = movies.page < movies.total_pages
            pagination.incrementOrSkip(pagination.hasNext)

            _state.postValue(UpcomingState.Show(result))
            pagination.isLoading = false
        }
    }

    fun reloadData() {
        pagination = Pagination()
        loadData()
    }
}

sealed class UpcomingState {
    class Show(val data: List<MovieItem>) : UpcomingState()
}