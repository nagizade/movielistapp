package com.hasannagizade.movielistapp.presentation.tabs.details

import android.os.Bundle
import androidx.fragment.app.Fragment
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

    lateinit var binding: FragmentMovieDetailsBinding

    val args: MovieDetailsArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater)

        val movie = args.movie

        Glide.with(binding.root.context)
            .load("http://image.tmdb.org/t/p/w500/${movie.poster_path}")
            .into(binding.poster)

        binding.description.text = movie.overview

        binding.back.setOnClickListener { findNavController().popBackStack() }

        return binding.root
    }
}