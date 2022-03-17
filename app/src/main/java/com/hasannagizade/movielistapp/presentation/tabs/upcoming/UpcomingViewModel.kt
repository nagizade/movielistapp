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
        getUpcoming.launch(
            MoviesUseCase.GetUpcoming.Params(pagination.nextPage),
            loadingHandle = {
                pagination.isLoading = it
            }
        ) {

            onSuccess = {

                val result = arrayListOf<MovieItem>()
                if (pagination.nextPage > 1) {

                    (_state.value as? UpcomingState.Show)?.data?.let { cachedList ->
                        result.addAll(cachedList)
                    }
                }

                result.addAll(it.results)
                _state.postValue(UpcomingState.Show(result))

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

sealed class UpcomingState {
    class Show(val data: List<MovieItem>) : UpcomingState()
}