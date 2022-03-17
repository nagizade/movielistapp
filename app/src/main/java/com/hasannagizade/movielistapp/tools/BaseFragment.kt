package com.hasannagizade.movielistapp.tools

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hasannagizade.movielistapp.R
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

abstract class BaseFragment<ViewModel : BaseViewModel> : Fragment() {


    protected abstract val vmClazz: KClass<ViewModel>
    protected abstract val screenName: String

    protected val viewModel: ViewModel by lazy { getViewModel(vmClazz) { parametersOf(arguments) } }

    fun showToast(message: String?) {
        val finalMessage = if (message.isNullOrEmpty()) {
            getString(R.string.message_error_happened)
        } else {
            message
        }

        Toast.makeText(requireContext(), finalMessage, Toast.LENGTH_SHORT).show()
    }
}