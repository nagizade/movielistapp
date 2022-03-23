package com.hasannagizade.movielistapp.tools

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasannagizade.movielistapp.usecases.BaseUseCase
import com.hasannagizade.movielistapp.usecases.CompletionBlock
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

open class BaseViewModel() :
    ViewModel(), KoinComponent {

    protected val _isLoading = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading


    suspend fun <P, R> BaseUseCase<P, R>.getWith(param: P): R {
        var result: R? = null
        val block: CompletionBlock<R> = {
            onSuccess = { result = it }
            onError = { throw it }
        }
        execute(param, block)
        return result!!
    }

    fun launchAll(
        loadingHandle: (Boolean) -> Unit = ::handleLoading,
        block: suspend () -> Unit
    ) {
        viewModelScope.launch {
            try {
                loadingHandle(true)
                block()
            } catch (t: Throwable) {
            } finally {
                loadingHandle(false)
            }
        }
    }

    private fun <T> Flow<T>.handleLoading(loadingHandle: (Boolean) -> Unit): Flow<T> = flow {
        this@handleLoading
            .onStart { loadingHandle(true) }
            .collect { value ->
                loadingHandle(false)
                emit(value)
            }
    }

    private fun <T> Flow<T>.handleError(): Flow<T> = catch {  }

    protected fun handleLoading(state: Boolean) {
        _isLoading.postValue(state)
    }
}