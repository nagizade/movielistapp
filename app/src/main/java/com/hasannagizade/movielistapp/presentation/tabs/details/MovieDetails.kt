package com.hasannagizade.movielistapp.presentation.tabs.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.hasannagizade.movielistapp.R
import com.hasannagizade.movielistapp.databinding.FragmentMovieDetailsBinding
import com.hasannagizade.movielistapp.tools.BaseFragment
import kotlin.reflect.KClass

class MovieDetails : BaseFragment<MovieDetailsViewModel>() {

    override val vmClazz: KClass<MovieDetailsViewModel>
        get() = MovieDetailsViewModel::class
    override val screenName: String
        get() = "movie_details"

    private lateinit var binding: FragmentMovieDetailsBinding

    private val args: MovieDetailsArgs by navArgs()

    private var isMovieLocal = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater)

        val movie = args.movie
        viewModel.getMovieFromWatchlist(movie.id)

        Glide.with(binding.root.context)
            .load("http://image.tmdb.org/t/p/w500/${movie.poster_path}")
            .into(binding.poster)

        binding.description.text = movie.overview

        binding.back.setOnClickListener { findNavController().popBackStack() }

        binding.watchlistAction.setOnClickListener {
            if (isMovieLocal) {
                viewModel.removeFromWatchlist(movie.id)
            } else viewModel.addToWatchlist(movie)
        }

        viewModel.state.observe(viewLifecycleOwner, { state ->
            when (state) {
                MovieDetailsState.SuccessAddWatchlist -> {
                    showToast(getString(R.string.added_to_watchlist))
                    findNavController().popBackStack()
                }
                MovieDetailsState.SuccessRemoveWatchlist -> {
                    showToast(getString(R.string.removed_from_watchlist))
                    findNavController().popBackStack()
                }
                is MovieDetailsState.LocalMovie -> {
                    state.movieItem?.let {
                        isMovieLocal = true
                        binding.watchlistAction.text = getString(R.string.remove_from_watchlist)
                    } ?: run {
                        binding.watchlistAction.text = getString(R.string.add_to_watchlist)
                        isMovieLocal = false
                    }
                }
            }
        })

        return binding.root
    }
}